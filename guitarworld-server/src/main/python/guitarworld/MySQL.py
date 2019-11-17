import pymysql

db = pymysql.connect("localhost", "root", "123456", "guitarworld_server")


def insert_informations(informations):
    cur = db.cursor()
    for information in informations:
        insert_sql = "INSERT INTO information(title, image_url, detail_url) VALUES(%s, %s, %s);"
        values = (information[0], information[1], information[2])
        print(values)
        cur.execute(insert_sql, values)
    cur.close()
    db.commit()
    db.close()


def clear_informations():
    cur = db.cursor()
    cur.execute("DELETE FROM information")
