from lib.Databases import List, engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy import Table, MetaData, select, update

Base = declarative_base()
session = sessionmaker(bind=engine)()
metadata = MetaData(bind=None)


def addStrike(userId, userName, reason, strike, date):
    userList = List(userId, userName, reason, strike, date)
    session.add(userList)
    session.commit()


def userExists(userId):
    table = Table('list', metadata, autoload=True, autoload_with=engine)
    stmt = select([table.columns.userId]).where(table.columns.userId == userId)
    return session.execute(stmt).fetchall() != []


def getStrikes(userId):
    table = Table('list', metadata, autoload=True, autoload_with=engine)
    stmt = select([table.columns.strike]).where(table.columns.userId == userId)
    for val in session.execute(stmt).fetchall():
        for num in val:
            return num


def updateStrikes(userId, actual):
    table = Table('list', metadata, autoload=True, autoload_with=engine)
    stmt = update(table).values(strike=actual+1).where(table.columns.userId == userId)
    session.execute(stmt)
    session.commit()
