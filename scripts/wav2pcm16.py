#!/usr/bin/python3

import sys
import wave

def main(inFile, outFile):
	w = wave.open(inFile, 'rb')

	if w.getframerate() != 44100:
		print('Wrong framerate:', w.getframerate())
		return 1

	if w.getnchannels() != 1:
		print('Wrong getnchannels:', w.getnchannels())
		return 1

	print("getsampwidth ", w.getsampwidth())
	print("getnframes ", w.getnframes())

	with open(outFile, 'wb') as f:
		f.write(w.readframes(w.getnframes()))


if __name__ == "__main__":
	if len(sys.argv) != 3 and False:
		print("<wav_file> <my_file>")
		sys.exit(1)

	#main(sys.argv[1], sys.argv[2])
	main('/home/evgeny/Projects/skinny_metronome/app/src/main/res/raw/click.wav', "/home/evgeny/Projects/skinny_metronome/app/src/main/res/raw/click.dat")