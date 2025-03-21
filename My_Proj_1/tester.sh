#!/bin/bash

# If running this script locally, you may need to remove the "timeout 5" parts

runner=$1

echo "Attempting to compile java code..."
javac *.java
runner="java Main"

score=0
error=0

for value in {1..17}
do
	echo ""
	echo "Running ${value}.code"
	${runner} Correct/${value}.code > Correct/${value}.student
	echo ""
	echo "Comparing with ${value}.expected"
	#Check for correct print
	tr -d '[:space:]' < Correct/${value}.student > temp1
	tr -d '[:space:]' < Correct/${value}.expected > temp2
	echo "Comparing input and output"
	if cmp -s "temp1" "temp2"; then
		echo "Print looks good"
		score=$(($score + 1))
	else
		echo "Student output and expected output are different"
	fi
done

rm temp1
rm temp2

echo ""
echo ""

echo "Running error cases:"
echo ""
echo "Running 1.error:"
echo "----------"
${runner} Error/1.code
echo "----------"
read -n 1 -p "Error is '!' in file. Error message related to that? (y/n)" mainmenuinput
if [ $mainmenuinput = "y" ]; then
	error=$(($error + 1))
fi

echo ""

echo "Running error cases:"
echo ""
echo "Running 2.error:"
echo "----------"
${runner} Error/2.code
echo "----------"
read -n 1 -p "Error is '?' in file. Error message related to that? (y/n)" mainmenuinput
if [ $mainmenuinput = "y" ]; then
	error=$(($error + 1))
fi

echo ""

echo "Running error cases:"
echo ""
echo "Running 3.error:"
echo "----------"
${runner} Error/3.code
echo "----------"
read -n 1 -p "Error is '$' in file. Error message related to that? (y/n)" mainmenuinput
if [ $mainmenuinput = "y" ]; then
	error=$(($error + 1))
fi

echo ""

echo "Running error cases:"
echo ""
echo "Running 4.error:"
echo "----------"
${runner} Error/4.code
echo "----------"
read -n 1 -p "Error is too large constant in file. Error message related to that? (y/n)" mainmenuinput
if [ $mainmenuinput = "y" ]; then
	error=$(($error + 1))
fi

echo ""

echo "Correct cases score out of 13:"
echo $score
echo "Error cases score out of 4:"
echo $error


echo Done!
