import csv, json, sys
#if you are not using utf-8 files, remove the next line
# sys.setdefaultencoding("UTF-8") #set the encode to utf8
#check if you pass the input file and output file

csv_columns = ['word', 'definition']

if sys.argv[1] is not None and sys.argv[2] is not None:
    fileInput = sys.argv[1]
    csv_file = sys.argv[2]
    inputFile = open(fileInput) #open json file
    data = json.load(inputFile) #load json content
    inputFile.close() #close the input file
   

try:
    with open(csv_file, 'w') as csvfile:
        writer = csv.writer(csvfile)
        # writer.writeheader()
        for key, value in data.items():
            print(key, data[key])
            writer.writerow([key.encode("utf-8"), value.encode("utf-8")])
except IOError:
    print("I/O error")