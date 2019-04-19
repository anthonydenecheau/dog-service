package com.scc.dog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.scc.dog.config.ServiceConfig;
import com.scc.dog.model.Breeder;
import com.scc.dog.model.Dog;
import com.scc.dog.model.Owner;
import com.scc.dog.model.Parent;
import com.scc.dog.repository.DogRepository;
import com.scc.dog.template.BreedObject;
import com.scc.dog.template.BreederObject;
import com.scc.dog.template.ChampionObject;
import com.scc.dog.template.DogObject;
import com.scc.dog.template.OwnerObject;
import com.scc.dog.template.PedigreeObject;
import com.scc.dog.template.ResponseObjectList;
import com.scc.dog.template.TitleObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class DogService {

   private static final Logger logger = LoggerFactory.getLogger(DogService.class);

   @Autowired
   private Tracer tracer;

   @Autowired
   private BreederService breederService;

   @Autowired
   private OwnerService ownerService;

   @Autowired
   private TitleService titleService;

   @Autowired
   private PedigreeService pedigreeService;

   @Autowired
   private DogRepository dogRepository;

   @Autowired
   private ParentService parentService;

   @Autowired
   ServiceConfig config;

   public DogObject getDogById(int dogId) {
      Span newSpan = tracer.createSpan("getDogById");
      logger.debug("In the dogService.getDogById() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

      DogObject _response = new DogObject();

      try {
         Dog _dog = new Dog();
         _dog = dogRepository.findById(dogId);

         if (_dog == null) {
            return _response.withId(0);
         } else {
            _response.withId(_dog.getId()).withGender(_dog.getSexe()).withBirthDate(_dog.getDateNaissance())
                  .withBirthCountry(_dog.getPays()).withPedigrees(searchPedigrees(_dog.getId()))
                  .withTokens(searchTokens(_dog.getTatouage(), _dog.getTranspondeur())).withBreed(searchBreed(_dog))
                  .withFather(searchParent(_dog.getIdEtalon(), _dog.getInscriptionCode()))
                  .withMother(searchParent(_dog.getIdLice(), _dog.getInscriptionCode()))
                  .withBreeder(searchBreeder(_dog.getId())).withOwners(searchOwners(_dog.getId()))
                  .withTitles(searchTitles(_dog.getId())).withNom(_dog.getNom()).withAffixe(_dog.getAffixe())
                  //.withWorkingClassPermit(_dog.getOnTravail())
                  ;
            _response.withName(buildName(_response.getNom(), _response.getAffixe(),
                  (_response.getBreeder() == null ? "O" : _response.getBreeder().getOnSuffixe())));
         }

         return _response;
      } finally {
         newSpan.tag("peer.service", "postgres");
         newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
         tracer.close(newSpan);
      }

   }

   @HystrixCommand(fallbackMethod = "buildFallbackDogList", threadPoolKey = "dogByTokenThreadPool", threadPoolProperties = {
         @HystrixProperty(name = "coreSize", value = "30"),
         @HystrixProperty(name = "maxQueueSize", value = "10") }, commandProperties = {
               @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
               @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
               @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
               @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
               @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5") })
   public ResponseObjectList<DogObject> getDogByToken(String token) {

      Span newSpan = tracer.createSpan("getDogByToken");
      logger.debug("In the dogService.getDogByToken() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
      try {
         /*
          * norme ISO (FDXB) = 15 chiffres
          */
         String regex = "^[0-9]{15}$";
         List<Dog> list = new ArrayList<Dog>();
         if (!token.matches(regex))
            list = dogRepository.findByTatouageIgnoreCase(token);
         else
            list = dogRepository.findByTranspondeur(token);

         List<DogObject> results = new ArrayList<DogObject>();

         results = list.stream()
               .map(_dog -> new DogObject().withId(_dog.getId()).withGender(_dog.getSexe())
                     .withBirthDate(_dog.getDateNaissance()).withBirthCountry(_dog.getPays())
                     .withPedigrees(searchPedigrees(_dog.getId()))
                     .withTokens(searchTokens(_dog.getTatouage(), _dog.getTranspondeur())).withBreed(searchBreed(_dog))
                     .withFather(searchParent(_dog.getIdEtalon(), _dog.getInscriptionCode()))
                     .withMother(searchParent(_dog.getIdLice(), _dog.getInscriptionCode()))
                     .withBreeder(searchBreeder(_dog.getId())).withOwners(searchOwners(_dog.getId()))
                     .withTitles(searchTitles(_dog.getId())).withNom(_dog.getNom()).withAffixe(_dog.getAffixe())
                     //.withWorkingClassPermit(_dog.getOnTravail())
                     )
               .map(_dog -> {
                  _dog.withName(buildName(_dog.getNom(), _dog.getAffixe(),
                        (_dog.getBreeder() == null ? "O" : _dog.getBreeder().getOnSuffixe())));
                  return _dog;
               }).collect(Collectors.toList());

         return new ResponseObjectList<DogObject>(results.size(), results);

      } finally {
         newSpan.tag("peer.service", "postgres");
         newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
         tracer.close(newSpan);
      }
   }

   private BreedObject searchBreed(Dog _dog) {

      BreedObject _breed = new BreedObject();

      try {

         _breed.setId(_dog.getIdRace());
         _breed.setFciNumber(_dog.getCodeFci());

         if (_dog.getRace() != null) {
            Map<String, Object> _name = new HashMap<String, Object>();
            _name.put("fr", _dog.getRace());
            _breed.setName(_name);
         }

         if (_dog.getCouleur() != null) {
            Map<String, Object> _couleur = new HashMap<String, Object>();
            _couleur.put("fr", _dog.getCouleur());
            _breed.setColor(_couleur);
         }

         //if (_dog.getVariete() != null) {
         if ( _dog.getIdVariete() > 0) {
            Map<String, Object> _variete = new HashMap<String, Object>();
            _variete.put("id", _dog.getIdVariete());
            _variete.put("fr", _dog.getVariete());
            _breed.setVariety(_variete);
         }

      } catch (Exception e) {

      }
      return _breed;
   }

   private String buildName(String _name, String _affixe, String _onSuffixe) {
      String result = "";

      try {

         if (_onSuffixe == null)
            _onSuffixe = "O";

         if (_affixe != null && !"".equals(_affixe)) {
            if (_onSuffixe.equals("O")) {
               result = _name + " " + _affixe;
            } else {
               result = _affixe + " " + _name;
            }
         } else {
            result = _name;
         }

      } catch (Exception e) {

      }

      return result;
   }

   private HashMap<String, Object> searchParent(int _id, String _inscription) {

      HashMap<String, Object> _info = new HashMap<String, Object>();
      Parent _parent = new Parent();

      try {

         /*
          * le géniteur n'est pas renseigné, on regarde le type d'inscription du sujet Si
          * le type d'inscription = A TITRE INITIAL, on le remonte dans les infos
          * géniteurs
          */
         if (_id == 0) {
            if (_inscription.equals("A TITRE INITIAL"))
               _info.put("name", "TI");
            else
               _info = null;
         } else {
            _parent = parentService.getParentById(_id);
            _info.put("name", buildName(_parent.getName(), _parent.getAffixe(), _parent.getOnSuffixe()));
         }
      } catch (Exception e) {

      }

      return _info;
   }

   private BreederObject searchBreeder(int _id) {

      Breeder _breeder = new Breeder();
      BreederObject _b = new BreederObject();
      try {
         _breeder = breederService.getBreederByIdDog(_id);
         
         if (_breeder==null)
            return _b;
         
         /*
          * Gestion de la règle particulier / eleveur (professionnel) La raison sociale
          * remplace le nom de l'éleveur; le prénom et la civilité sont réinitialisés.
          */
         if (_breeder.getTypeProfil().equals("E") && _breeder.getProfessionnelActif().equals("O")
               && (_breeder.getRaisonSociale() != null && !"".equals(_breeder.getRaisonSociale()))) {
            _breeder.setLastName(_breeder.getRaisonSociale());
            _breeder.setFirstName("");
            _breeder.setCivility("");
         }


         _b.withCivility(_breeder.getCivility()).withLastName(_breeder.getLastName())
               .withFirstName(_breeder.getFirstName()).withCountry(_breeder.getPays())
               .withOnSuffixe(_breeder.getOnSuffixe());

      } catch (Exception e) {

      }
      return _b;
   }

   private List<OwnerObject> searchOwners(int _id) {

      List<OwnerObject> _owners = new ArrayList<OwnerObject>();
      try {

         Owner _owner = ownerService.getOwnerByIdDog(_id);
         _owners.add((OwnerObject) new OwnerObject().withLastName(_owner.getLastName())
               .withFirstName(_owner.getFirstName()).withAddress(_owner.getAddress()).withZipCode(_owner.getZipCode())
               .withTown(_owner.getTown()).withCountry(_owner.getCountry()));

      } catch (Exception e) {

      }
      return _owners;

   }

   private List<TitleObject> searchTitles(int _id) {

      return titleService.getTitlesByIdDog(_id).stream()
            .map(_title -> new TitleObject().withName(_title.getName()).withCountry(_title.getCountry())
                  .withObtentionDate(_title.getObtentionDate()).withTitle(_title.getTitle()).withType(_title.getType()))
            .collect(Collectors.toList());

   }

   private List<PedigreeObject> searchPedigrees(int _id) {

      return pedigreeService.getPedigreesByIdDog(_id).stream()
            .map(_pedigree -> new PedigreeObject().withNumber(_pedigree.getNumber()).withCountry(_pedigree.getCountry())
                  .withType(_pedigree.getType()).withObtentionDate(_pedigree.getObtentionDate()))
            .collect(Collectors.toList());

   }

   private List<Map<String, Object>> searchTokens(String _tattoo, String _chip) {

      List<Map<String, Object>> tokens = new ArrayList<Map<String, Object>>();
      Map<String, Object> token = new HashMap<String, Object>();
      logger.debug("searchTokens: tattoo {}, chip {}", _tattoo, _chip);
      try {
         token.clear();
         if (_tattoo != null && !"".equals(_tattoo)) {
            token.put("type", "tattoo");
            token.put("number", _tattoo);
            tokens.add(new HashMap<String, Object>(token));
         }
         token.clear();
         if (_chip != null && !"".equals(_chip)) {
            token.put("type", "chip");
            token.put("number", _chip);
            tokens.add(new HashMap<String, Object>(token));
         }

      } catch (Exception e) {
         logger.error("searchTokens: tattoo {}, chip {}, error {}", _tattoo, _chip, e.getMessage());
      }
      return tokens;

   }

   @SuppressWarnings("unused")
   private ResponseObjectList<DogObject> buildFallbackDogList(String token) {

      logger.debug("In the dogService.buildFallbackDogList() call");

      List<DogObject> list = new ArrayList<DogObject>();
      list.add(new DogObject().withId(0));
      return new ResponseObjectList<DogObject>(list.size(), list);
   }

   @HystrixCommand(fallbackMethod = "buildFallbackChampionList", threadPoolKey = "championChangeThreadPool", threadPoolProperties = {
         @HystrixProperty(name = "coreSize", value = "30"),
         @HystrixProperty(name = "maxQueueSize", value = "10") }, commandProperties = {
               @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
               @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
               @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
               @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
               @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5") })
   public ResponseObjectList<ChampionObject> getChampionsChanges(String referenceDate) {

      Span newSpan = tracer.createSpan("getChampionsChanges");
      logger.debug("In the dogService.getChampionsChanges() call, trace id: {}",
            tracer.getCurrentSpan().traceIdString());

      List<ChampionObject> results = new ArrayList<ChampionObject>();

      try {

         // Lecture des id distinct
         results = titleService.findByObtentionDateGreaterThanEqual(referenceDate).stream()
               .map(_title -> new ChampionObject().withId(_title.getIdDog())).filter(distinctByKey(p -> p.getId()))
               .collect(Collectors.toList());

      } catch (Exception e) {
         logger.error("In the dogService.getChampionsChanges() call, trace id: {}, error {}",
               tracer.getCurrentSpan().traceIdString(), e.getMessage());
      } finally {
         newSpan.tag("peer.service", "postgres");
         newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
         tracer.close(newSpan);
      }

      return new ResponseObjectList<ChampionObject>(results.size(), results);

   }

   public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
      Map<Object, Boolean> map = new ConcurrentHashMap<>();
      return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
   }

   @SuppressWarnings("unused")
   private ResponseObjectList<ChampionObject> buildFallbackChampionList(String referenceDate) {

      logger.debug("In the dogService.buildFallbackChampionList() call");

      List<ChampionObject> list = new ArrayList<ChampionObject>();
      list.add(new ChampionObject().withId(0));
      return new ResponseObjectList<ChampionObject>(list.size(), list);
   }

   public void save(Dog syncDog, Long timestamp) {

      try {
         Dog dog = dogRepository.findById(syncDog.getId());
         if (dog == null) {
            logger.debug("Dog id {} not found", syncDog.getId());
            syncDog.withTimestamp(new Timestamp(timestamp));
            dogRepository.save(syncDog);
         } else {
            logger.debug("save dog id {}, {}, {}", dog.getId(), dog.getTimestamp().getTime(), timestamp);
            if (dog.getTimestamp().getTime() < timestamp) {
               logger.debug("check queue OK ; call saving changes ");
               dog.withNom(syncDog.getNom()).withAffixe(syncDog.getAffixe()).withSexe(syncDog.getSexe())
                     .withDateNaissance(syncDog.getDateNaissance()).withPays(syncDog.getPays())
                     .withTatouage(syncDog.getTatouage()).withTranspondeur(syncDog.getTranspondeur())
                     .withCodeFci(syncDog.getCodeFci()).withIdRace(syncDog.getIdRace())
                     .withIdVariete(syncDog.getIdVariete()).withRace(syncDog.getRace())
                     .withVariete(syncDog.getVariete()).withCouleur(syncDog.getCouleur())
                     .withCouleurAbr(syncDog.getCouleurAbr()).withInscriptionCode(syncDog.getInscriptionCode())
                     .withIdEtalon(syncDog.getIdEtalon()).withIdLice(syncDog.getIdLice())
                     //.withOnTravail(syncDog.getOnTravail())
                     .withTimestamp(new Timestamp(timestamp));

               dogRepository.save(dog);
            } else
               logger.debug("check queue KO : no changes saved");

         }
      } finally {

      }
   }

   public void deleteById(int idDog) {
      try {
         dogRepository.deleteById(idDog);
      } finally {

      }
   }

}
