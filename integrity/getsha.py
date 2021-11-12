# Python program to find SHA256 hexadecimal hash string of a file
import hashlib
import os
import glob

path = "..//out//production//JavaFxApplication//"
files = [f for f in glob.glob(path + "**/*.*", recursive=True)]

print("check files")
xs = bytearray(b'')

for filename in files:
    print(filename)
    with open(filename,"rb") as f:
        bytes = f.read() # read entire file as bytes
        xs.extend(bytes)
print("sha 256 --------------------------------------")
readable_hash = hashlib.sha256(xs).hexdigest();
print(readable_hash)
print("-----------------------------------------------")




