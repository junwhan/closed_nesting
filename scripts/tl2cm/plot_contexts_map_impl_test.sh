#!/bin/sh

i=128
w=50
cm=Karma

for b in RBTree; do #IntJavaHashSet LinkedList RBTree SkipList; do
	j=0
	for mapImpl in CHM COWAL RWL ARR HM; do
	    data=logs/intset-${b}-tl2cm-${cm}-${mapImpl}-w${w}.data
	    rm -f ${data}
	    for t in 1 2 4 8 12 16 20 24 28 32; do
		    sumIterations=0
		    sumDuration=0
			for iter in 1 2 3 4 5; do
				log=logs/${iter}-intset-${b}-tl2cm-${mapImpl}-Karma-w${w}-t${t}.log				
				iterations=`grep "Nb iterations" ${log} | awk '{ s += $4; nb++ } END { printf "%f", s/nb }'`
		        duration=`grep "Test duration" ${log} | awk '{ s += $5; nb++ } END { printf "%f", s/nb/1000 }'`
				sumIterations=`echo "$sumIterations + $iterations" | bc`
				sumDuration=`echo "$sumDuration + $duration" | bc`
		    done
			sumIterations=`echo "tmp=$sumIterations; tmp /= 1; tmp" | bc`
			sumDuration=`echo "tmp=$sumDuration; tmp /= 1; tmp" | bc`
			echo "${t} ${sumIterations} ${sumDuration}" >> ${data}
	    done
	    j=`expr ${j} \+ 1`
	done
	
	g=logs/graph-intset-${b}.gp
	eps=logs/graph-intset-${b}.eps
	
	echo "set term postscript eps enhanced color 22" > ${g}
	echo "set output \"${eps}\"" >> ${g}
	echo "set size 2,2" >> ${g}
	echo "set title \"IntSet ${b}, size=${i}, update=${w}%\"" >> ${g}
	echo "set key bottom" >> ${g}
	echo "set key right" >> ${g}
	echo "set xtics 2" >> ${g}
	echo "set xlabel \"Number of threads\"" >> ${g}
	echo "set ylabel \"Throughput (transactions/s)\"" >> ${g}
	echo -n "plot " >> ${g}
	
	for mapImpl in CHM COWAL RWL ARR HM; do
		data=logs/intset-${b}-tl2cm-${cm}-${mapImpl}-w${w}.data
	    echo -n "\"${data}\" using 1:(\$2/\$3) title \"${mapImpl}\" with lines" >> ${g}
	    j=`expr ${j} \- 1`
	    if [ ${j} -gt 0 ]; then
	        echo ", \\" >> ${g}
	        echo -n "    " >> ${g}
	    else
	        echo "" >> ${g}
	    fi
	done
	gnuplot ${g}
	epstopdf ${eps}
done
