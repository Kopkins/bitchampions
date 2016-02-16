#!/bin/bash
# This script moves the source files for the project 
# to the appropriate locations for Maven to build it.
#
# This must be run from the same directory holding pom.xml
# and expects one argument which is the src directory.

build_dir="uml-app/src/main/java/bitchampions/"
build_dir_test="uml-app/src/test/java/bitchampions/"

if [ -d $build_dir ]; then
	echo "Build Dir found"
else
	mkdir -p $build_dir
fi
if [ -d $build_dir_test ]; then
	echo "Tests Build Dir found"
else
	mkdir -p $build_dir_test
fi

if [ $1 ]; then
	
	tests=$(find $1 -regex '.*test.*\.java')
	for test in $tests
	do
		cp $test $build_dir_test
	done
	sources=$(find $1 -regex '.*\.java')
	for source in $sources
	do
		cp $source $build_dir
	done
	mvn compile
	mvn package

	

else
	echo "Usage:"
	echo "  ./build.sh path/to/source/code/"
	exit
fi
