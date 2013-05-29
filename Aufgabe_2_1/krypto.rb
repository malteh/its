require 'matrix'
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
r.permutation(2)
end

def is_invertable(matrix)
(1/(matrix[0,0]*matrix[1,1]-matrix[0,1]*matrix[1,0])) != 0
end


"SOLVED".bytes.permutation(2).each do |p1,p2| 
"GEZXDS".bytes.permutation(2).each do |c1,c2|
a = p1[0] - ('A'.getbyte 0)
b = p1[1] - ('A'.getbyte 0)
c = p2[0] - ('A'.getbyte 0)
d = p2[1] - ('A'.getbyte 0)
matrix = Matrix[a,b,c,d]
if is_invertable(matrix)
if matrix.interse*Matrix[c1[0],c1[1],c2[0],c2[1]]==matrix
puts "Found"
end
end
end

end
