#!/bin/bash
# Kyle Hopkins

# This script moves the source files for the project 
# to the appropriate locations for Maven to build it.
#
# This must be run from the same directory holding pom.xml
# and expects one argument which is the src directory.

build_dir_base="uml-app"
build_dir="uml-app/src/main/java/bitchampions/"
build_dir_test="uml-app/src/test/java/bitchampions/"

# Clean out old builds
if [ -d $build_dir_base ]; then
	echo "Build Dir found ... cleaning"
	rm -R $build_dir_base
fi

# Create new build directory
mkdir -p $build_dir
mkdir -p $build_dir_test

if [ $1 ]; then
	
	sources=$(find $1 -regex '.*\.java')
	for source in $sources
	do
		echo $source | grep -qi test
		if [ $? == 0 ]; then
			#cp $source $build_dir_test
			echo "Skipping test $source"
		else
			cp $source $build_dir
		fi
	done

	cp pom.xml $build_dir_base
	cd $build_dir_base
	mvn compile
	mvn package

else
	echo "Usage:"
	echo "  ./build.sh path/to/source/code/"
fi
