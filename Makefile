ORIGIN ?= origin

build:
	./gradlew build

run:
	./gradlew run

report:
	./gradlew jacocoTestReport

test:
	./gradlew test

sonar:
	./gradlew sonar --info

clean:
	./gradlew clean

check:
	./gradlew checkStyleMain checkStyleTest

.PHONY: build report test sonar check run

.PHONY: fetch-github
fetch-github: ORIGIN := github
fetch-github: fetch

.PHONY: fetch
fetch:
	git fetch $(ORIGIN) --prune

.PHONY: sync
sync:
	git branch -f master github/HEAD
	git push origin master:master
