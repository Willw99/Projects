def convertToNum(character):
	return ord(character)-65

def convertToChar(numChar):
	return chr(numChar+65)

def encrypt(character, shift):
	charNum=convertToNum(character)
	output=charNum+shift
	if output > 25:
		output=output-26
	return convertToChar(output)

def decrypt(character, shift):
	charNum=convertToNum(character)
	output=charNum-shift
	if output < 0:
		output=output+26
	return convertToChar(output)

def encryption():
	text=input("Please enter text for encryption: ").upper()
	output=""
	key=input("Please enter a key: ").upper()
	#key=key.upper()
	#text=text.upper()
	counter = 0
	for d in text:
		if d == ' ':
			output = output + " "
		else:
			if counter > len(key)-1:
				counter = 0
			#print(convertToNum(key[counter]))
			output = output + encrypt(d,convertToNum(key[counter]))
		counter=counter+1
	print(output.lower())
		
def decryption():
	text=input("Please enter encrypted text: ").upper()
	output=""
	key=input("Please enter a key: ").upper()
	counter = 0
	for d in text:
		if d == ' ':
			output = output + " "
		else:
			if counter > len(key)-1:
				counter = 0
			#print(convertToNum(key[counter]))
			output = output + decrypt(d,convertToNum(key[counter]))
		counter=counter+1
	print(output.lower())
		
def printMenu():
	print("1. Encrypt a Message.")
	print("2. Decrypt a Message.")
	print("Q. Quit")
	inputText=input("Enter the option you'd like to select: ")
	return inputText

inputText = ""
while inputText.upper() != "Q":
	inputText=printMenu()
	if inputText == "1":
		encryption()
	elif inputText == "2":
		decryption()

	elif inputText.upper() == "Q":
		print("Quitting....")
	else:
		print("INVALID INPUT")
