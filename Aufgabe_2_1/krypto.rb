
# Aufgabe a
def checkstring(s)
	sum = 0
	s.reverse.chars.each_index do |index|
		if "A" == CIFFRETEXT[index]
		sum += 1
		end
	end
	sum
end



CIFFRETEXT = "ABABABBBAB"
key = CIFFRETEXT
max = {}
max[:string]=""
max[:value]=0
while key.size > 0 do
	size = checkstring(key)
	if size>max[:value]
	max[:value]=size
	max[:string]=key
	end	
	key = key[1...-1]
end



puts "Maximale String:" 
puts max[:string]
puts "Maximaler Wert:"
puts max[:value]

# Entschlüsseln

((max[:value]/2) ... (max[:value]*2)+1).step(max[:value]/2).each do |value|
	e = []
	CIFFRETEXT.chars.each do |char|
			e << ((char.getbyte 0) % value)
	end
	puts "Entschlüsselter Text mit Key von: " +value.to_s
	puts e.join
end

# Aufgabe b

def reduce_to_null(s)
r = []
	s.bytes.each do |char|
		r << (char.getbyte 0)-('A'.getbyte 0)
	end
r.join
end

def enc_hill(message,key)
vector = message*key
vector_mod vector
end

def dec_hill(message,key)
vector = message.inverse*key
vector_mod vector
end

def vector_mod(vector)
result=[]
vector.each do |elem|
	result << elem % 26
result
end
