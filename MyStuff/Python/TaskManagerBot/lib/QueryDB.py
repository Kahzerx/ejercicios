from sqlalchemy import create_engine, Column, String, Integer
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

Base = declarative_base()

engine = create_engine('sqlite:///database/database.db', echo=True)
session = sessionmaker(bind=engine)()


class Task(Base):
    __tablename__ = "tasks"

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


def insertTask(userId, msg, date, completed):
    task = Task(userId, msg, date, completed)
    session.add(task)
    session.commit()

