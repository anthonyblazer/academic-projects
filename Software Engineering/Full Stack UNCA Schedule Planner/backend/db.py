import os

from dotenv import load_dotenv
from sqlalchemy import create_engine
from sqlalchemy.engine import URL
from sqlalchemy.ext.asyncio import AsyncSession, create_async_engine
from sqlalchemy.orm import DeclarativeBase, sessionmaker

load_dotenv()


# Base class for models
class Base(DeclarativeBase):

    def to_dict(self):
        return {c.name: getattr(self, c.name) for c in self.__table__.columns}


# DATABASE_URL = "postgresql+asyncpg://user:password@localhost:5432/course_planner_db"
DATABASE_URL = URL.create(
    drivername="postgresql+asyncpg",
    username=os.environ.get("DB_USERNAME"),
    password=os.environ.get("DB_PASSWORD"),
    host=os.environ.get("DB_HOST"),
    port=os.environ.get("DB_PORT"),
    database=os.environ.get("DB_NAME"),
)


DATABASE_URL_SYNC = URL.create(
    drivername="postgresql",
    username=os.environ.get("DB_USERNAME"),
    password=os.environ.get("DB_PASSWORD"),
    host=os.environ.get("DB_HOST"),
    port=os.environ.get("DB_PORT"),
    database=os.environ.get("DB_NAME"),
)
# Create database engines
engine = create_async_engine(DATABASE_URL, echo=True)
engine_sync = create_engine(DATABASE_URL_SYNC)

# Create session factory
AsyncSessionLocal = sessionmaker(
    bind=engine,
    class_=AsyncSession,
    expire_on_commit=False,
)

SessionLocal = sessionmaker(bind=engine_sync, autocommit=False, autoflush=False)


# Dependency to get the database session
async def get_db():
    async with AsyncSessionLocal() as session:
        yield session
