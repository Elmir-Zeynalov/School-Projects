#!/usr/bin/env python3

import collections
import subprocess
import sys
import ast
import re

def createSyntaxDictionary(syntaxFileName) :
	"""
	Load a dictionary of { type : regex } from a file with .syntax extension.
		@param	syntaxFileName		syntax file
		@return					    dictionary of { type : regex }
	"""

	syntax = {}

	try :
		with open(syntaxFileName, encoding="utf8") as inFile :
			for line in inFile :
				value = line.rsplit(':', 1)[0]
				value = value.split('"')

				for i in range(1,len(value)-2) :
					value[i] = value[i] + '"'
				value = ''.join(value[1:-1])
				keyWord 		= line.split()[-1]
				syntax[keyWord] = value
	except FileNotFoundError :
		print ("Syntax file not found")
		sys.exit(1)
	
	return syntax


def createThemeDictionary(themeFileName) :
	"""
	Load a dictionary of { type : color }
		@param	themeFileName - contains theme-map
		@return	theme dictionary of { type : color }
	"""

	theme = {}

	try :
		with open(themeFileName, encoding="utf8") as inFile :
			for line in inFile :
				line 	= line.split(':')
				keyWord = line[0]
				value 	= line[1].strip()
				theme[keyWord] = value
	except FileNotFoundError :
		print ("Theme file not found")
		sys.exit(1)
	
	return theme


def simpleArgumentParser(arguments) :
	""" Parses the command line arguments and returns file names.
		@param 	arguments 		command line arguments with highlighter.py
								stripped off
		@return 				syntax file, theme file, and code file
	"""
	if len(arguments) != 3 :
		print("Invalid number of inputs. Usage:")
		print("> python3 highlighter.py syntaxfile themefile codefile.")
		sys.exit(1)

	if re.search(r"\.syntax", arguments[0]) :
		syntaxFile = arguments[0]
	else :
		print("Inavlid syntaxfile. Must have file ending '.syntax'.")
		sys.exit(1)
	
	if re.search(r"\.theme", arguments[1]) :
		themeFile = arguments[1]
	else :
		print("Inavlid themefile. Must have file ending '.theme'.")
		sys.exit(1)
	
	codeFile = arguments[2]
	
	return syntaxFile, themeFile, codeFile


def printCodeInColor(syntax, theme, codeFile) :
	""" Outputs the contents of the codeFile argument in the specified colors.
	Syntax highligthing is done according to the syntax dictionary of { regex :
	type }, while the theme dictionary dictates the output color of the
	different types.
		@param 	syntax 		dictionary of {regex : type}
		@param	theme 		dictionary of {type : color}
		@param 	codeFile 	code file to print colorized
	"""
	try:
		with open(codeFile,'r', encoding="utf8") as inFile :
			code = inFile.read()
	except FileNotFoundError :
		print ("Code file not found")
		sys.exit(1)

	ANSI_RESET 	= r"\033[0m"

	# This is list of colors for each character in the incoming
	# code file. It is used as a way of keeping track of
	# the coloring "overlap".
	c = [ANSI_RESET]*len(code)
	i = 0
	for s in syntax:
		# In order to facilitate multiline comments, we apply the
		# DOTALL flag such that ".*" matches anything including
		# newline characters.
		if s == 'multiLineComment' :
			compiledRegex = re.compile(syntax[s], re.DOTALL)
		else :
			compiledRegex = re.compile(syntax[s])
		indices = compiledRegex.finditer(code)
		if indices :
			for j in indices :
				for k in range(j.span()[0], j.span()[1]) :
					c[k] = r"\033[{}m".format(theme[s])
		i += 1
	cString = ''.join([str(i) for i in c])

	code = str(c[0]) + code
	offSet = len(str(c[0]))
	for i in range(1,len(c)) :

		# If the color of the previous character in the code was
		# different from the color of the current one, we insert
		# the new ANSI color code.
		if (c[i] != c[i-1]) :
			code = code[:i+offSet] + str(c[i]) + code[i+offSet:]
			offSet += len(str(c[i]))

	# Ensure the entire string
	# is printed with the escaped characters replaced by their
	# "effect."
	decodedString = bytes(code, "utf-8").decode("unicode_escape")
	print(decodedString, end="")


if __name__ == '__main__' :
	subprocess.call('', shell=True)

	# If no files specified, use default(python)
	if len(sys.argv) == 1 :
		print("No files specified, defaulting to:")
		print("> python3 highlighter.py python.syntax python.theme code.py")
		syntaxFile = "python.syntax"
		themeFile  = "python.theme"
		codeFile   = "demo.py"
	else :
		syntaxFile, themeFile, codeFile = simpleArgumentParser(sys.argv[1:])

	theme 	= createThemeDictionary (themeFile)
	syntax 	= createSyntaxDictionary(syntaxFile)
	printCodeInColor(syntax, theme, codeFile)
	print()