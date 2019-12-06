import mysql.connector
from mysql.connector import errorcode

import csv





if __name__ == "__main__":
    try:
        cnx = mysql.connector.connect(user='root', password='password',
                                host='127.0.0.1',
                                database='BlankDictionary')
        cursor = cnx.cursor()

        with open('word_list.csv', newline='') as csvfile:
            reader = csv.reader(csvfile, delimiter=',')


            insert = ("INSERT INTO bhutia "
               "(eng_trans, bhut_rom_formal, bhut_rom_informal, bhut_script_formal, bhut_script_informal,  category, spoken_b, spoken_e) "
               "VALUES (%s, %s, %s, %s, %s, %s, %s, %s)")
           
            query = ("SELECT * FROM Bhutia")
           
           #example query to check connection to MySQL
            # cursor.execute(query)
            # for row in cursor:
            #     print(row)
            
            
           
            for row in reader:
                print(row)
                cursor.execute(insert, row)
                cnx.commit()



    except mysql.connector.Error as err:
        if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
            print("Something is wrong with your user name or password")
        elif err.errno == errorcode.ER_BAD_DB_ERROR:
            print("Database does not exist")
        else:
            print(err)
    else:
        cnx.close()

