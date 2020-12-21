current_tag=$(git tag --points-at)
latest_tag=$(git tag)
if test -z "$current_tag"
then
  echo "\033[0;31mError: HEAD doesn't have a tag (\033[0;37mlatest tag is $latest_tag\033[0;31m). If you need to publish your artifacts, create a release first.\033[0m"
else
  echo "\033[0;32mUploading \033[0;37mPress v-$current_tag\033[0;32m to Bintray\033[0m"
  ./gradlew clean build bintrayUpload
fi