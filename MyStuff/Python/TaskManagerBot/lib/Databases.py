import os
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import create_engine, Column, String, Integer


engine = create_engine('sqlite:///database/database.db', echo=False)
Base = declarative_base()


def tryCreateDatabase():
    tryCreatedir()
    Base.metadata.create_all(engine)


def tryCreatedir():
    if not os.path.exists('database'):
        os.makedirs('database')


class Task(Base):
    __tablename__ = 'tasks'

    id = Column(Integer, primary_key=True, autoincrement=True)
    userId = Column(Integer, nullable=False)
    msg = Column(String, nullable=False)
    date = Column(String, nullable=False)
    completed = Column(Integer, nullable=False)

    def __init__(self, userId, msg, date, completed):
        self.userId = userId
        self.msg = msg
        self.date = date
        self.completed = completed


class User(Base):
    __tablename__ = 'user'

    userId = Column(Integer, primary_key=True, nullable=False)
    name = Column(String, nullable=False)

    def __init__(self, userId, name):
        self.userId = userId
        self.name = name
