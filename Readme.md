[![Build Status](https://travis-ci.org/jwojciec/task-app.svg?branch=master)](https://travis-ci.org/jwojciec/task-app)

curl examples:
    curl -i -X GET localhost:2222/tasks
    curl -i -X POST localhost:2222/tasks -H "Content-Type: application/json" -d '{"name":"zmywanie"}'
    curl -i -X GET localhost:2222/tasks/1
    curl -i -X DELETE localhost:2222/tasks/1

