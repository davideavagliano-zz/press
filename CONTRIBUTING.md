# Contributing

## Commit convention

This repo **SHALL** follow the [Conventional Commits](https://www.conventionalcommits.org/en) specification. Also, sentences in commit messages made to this repo **SHALL** be written in _imperative present tense_ form.

## Releasing

These are the steps that **MUST** be performed in order to correctly and consistently set-up everything before releasing a new version of _Press_:
 - Update `version.code` and `version.name` (note that versioning follows [semver](https://semver.org/)) variables located into [press.properties](https://github.com/davideavagliano/press/blob/main/press.properties) file;
 - Update the [changelog](https://github.com/davideavagliano/press/blob/main/CHANGELOG.md) file by relying upon the _Conventional Commits_ specification;
 - Update the version contained in the link located at the very top of the [readme](https://github.com/davideavagliano/press/blob/main/README.md) file;
 - Create a commit with all these modifications, labeled `Release x.y.z` (where `x.y.z` are the new version's major, minor and patch values);
 - Create a tag with the same `x.y.z` values;
 - Push;
 - On your local machine, run `./sh publish.sh` from within the repo folder in order to publish the new version's artifact on _Bintray_.
