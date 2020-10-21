import os
import sqlite3


def tryCreateDatabase():
    tryCreateDBFile()

    connection = sqlite3.connect('database/database.db')
    cursor = connection.cursor()

    cursor.execute(userTable())
    cursor.execute(taskTable())
    connection.commit()
    connection.close()


def tryCreateDBFile():
    if not os.path.exists('database'):
        os.makedirs('database')

    if not os.path.isfile('database/database.db'):
        f = open("database/database.db", "w")
        f.close()


def userTable():
    return "CREATE TABLE IF NOT EXISTS `user` (" \
           "`userId` INTEGER PRIMARY KEY NOT NULL," \
           "`name` TEXT NOT NULL);"


def taskTable():
    return "CREATE TABLE IF NOT EXISTS `tasks` (" \
           "`id` INTEGER PRIMARY KEY AUTOINCREMENT," \
           "`userId` INTEGER NOT NULL," \
           "`msg` TEXT NOT NULL," \
           "`date` TEXT NOT NULL," \
           "`completed` INTEGER NOT NULL);"
