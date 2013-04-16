# 5.2

# Ein Zeichen Lang
./makepasswd -u test a > 1_1.pwd
./makepasswd -u test p > 1_2.pwd
# Zwei Zeichen Lang
./makepasswd -u test gh > 2_1.pwd
./makepasswd -u test tj > 2_2.pwd
# Drei Zeichen Lang
./makepasswd -u test dfi > 3_1.pwd
./makepasswd -u test uhh > 3_2.pwd
# Vier Zeichen Lang
./makepasswd -u test sfaa > 4_1.pwd
./makepasswd -u test otto > 4_2.pwd

# Dieses Wort ist im Wörterbuch
./makepasswd -u test xyzzx > 4_3.pwd

cd john-1.7.9/run
./john -i=alpha  ../../1_1.pwd
./john -i=alpha ../../1_2.pwd
./john -i=alpha ../../2_1.pwd
./john -i=alpha ../../2_2.pwd
./john -i=alpha ../../3_1.pwd
./john -i=alpha ../../3_2.pwd
./john -i=alpha ../../4_1.pwd
./john -i=alpha ../../4_2.pwd
./john -i=alpha ../../4_3.pwd
# 5.3
# ./john --wordlist=password.lst ../../4_3.pwd




