echo "Pushing service docker images to docker hub ...."
docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
docker push anthonydenecheau/scc-dog-service:$BUILD_NAME
