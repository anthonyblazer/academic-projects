from python:3.11
COPY ./backend /app
# COPY --from=0 /ui/dist /app/ui/dist
WORKDIR /app
RUN pip install poetry
RUN poetry install 