from sqlalchemy import Table, MetaData, select, insert, update, and_
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from lib.Databases import engine, Task

Base = declarative_base()

session = sessionmaker(bind=engine)()
metadata = MetaData(bind=None)


def insertTask(userId, msg, date, completed):
    task = Task(userId, msg, date, completed)
    session.add(task)
    session.commit()


def insertUser(userId1, name1):
    table = Table('user', metadata, autoload=True, autoload_with=engine)
    stmt = insert(table).prefix_with('OR IGNORE').values(userId=userId1, name=name1)
    session.execute(stmt)
    session.commit()


def getTodoTasks(userId):
    table = Table('tasks', metadata, autoload=True, autoload_with=engine)
    stmt = select([table.columns.msg, table.columns.date]).where(and_(table.columns.userId == userId,
                                                                      table.columns.completed == 0))
    result = session.execute(stmt).fetchall()
    if not result:
        return 'No tasks'

    tasks = ''

    for idX, item in enumerate(result):
        tasks += f'[{idX + 1}] {item[0]} --> {item[1]}\n'
    tasks += '\n.done 1 -- to mark as done'

    return tasks


def getDoneTasks(userId):
    table = Table('tasks', metadata, autoload=True, autoload_with=engine)
    stmt = select([table.columns.msg, table.columns.date]).where(and_(table.columns.userId == userId,
                                                                      table.columns.completed == 1))
    result = session.execute(stmt).fetchall()
    if not result:
        return 'No tasks'

    tasks = ''
    for idX, item in enumerate(result):
        tasks += f'[{idX + 1}] {item[0]} --> {item[1]}\n'
    tasks += '\n.unDone 1 -- to mark as not done'

    return tasks


def getId(msgId, userId, done):
    table = Table('tasks', metadata, autoload=True, autoload_with=engine)
    stmt = select([table.columns.id, table.columns.msg, table.columns.date]).where(and_(table.columns.userId == userId,
                                                                                        table.columns.completed == done))
    result = session.execute(stmt).fetchall()

    var = -1
    for idX, item in enumerate(result):
        if idX == msgId - 1:
            var = item[0]

    return var


def updateCompleted(rowId, done):
    table = Table('tasks', metadata, autoload=True, autoload_with=engine)
    stmt = update(table).values(completed=done).where(table.columns.id == rowId)
    session.execute(stmt)
    session.commit()


def getAllTasks(userId):
    table = Table('tasks', metadata, autoload=True, autoload_with=engine)
    stmt = select([table.columns.msg, table.columns.date, table.columns.completed]).where(and_(table.columns.userId == userId))
    result = session.execute(stmt).fetchall()
    if not result:
        return 'No tasks'

    tasks = ''
    for idX, item in enumerate(result):
        status = 'x' if item[2] == 0 else '✓'
        tasks += f'[{status}] {item[0]} --> {item[1]}\n'

    return tasks
