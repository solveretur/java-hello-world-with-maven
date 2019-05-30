from flask import Flask, abort, request, Response, json

app = Flask(__name__)


@app.route('/2018-06-01/runtime/invocation/next')
def nextEvent():
    resp = Response(json.dumps({"id": 314, "name": "Hieronim", "isHappy": "true"}))
    resp.headers['Lambda-Runtime-Aws-Request-Id'] = 'abcdefghijkl'
    return resp

@app.route('/2018-06-01/runtime/invocation/abcdefghijkl/response', methods=["POST"])
def handleResponse():
    req = request.get_data();
    print(req)
    return "ok"


if __name__ == '__main__':
    app.run()
