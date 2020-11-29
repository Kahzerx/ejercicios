import os
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import create_engine, Column, String, Integer

engine = create_engine('sqlite:///database/database.db', echo=False)
Base = declarative_base()


def tryCreateDatabase():
    tryCreateDir()
    Base.metadata.create_all(engine)


def tryCreateDir():
    if not os.path.exists('database'):
        os.makedirs('database')


class List(Base):
    __tablename__ = 'list'

    userId = Column(Integer, primary_key=True, nullable=False)
    userName = Column(String, nullable=False)
    reason = Column(String, nullable=False)
    strike = Column(Integer, nullable=False)
    date = Column(String, nullable=False)

    def __init__(self, userId, userName, reason, strike, date):
        self.userId = userId
        self.userName = userName
        self.reason = reason
        self.strike = strike
        self.date = date
