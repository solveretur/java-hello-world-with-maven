from flask import Flask, request, Response, json

app = Flask(__name__)


@app.route('/2018-06-01/runtime/invocation/next')
def nextEvent():
    data = {
        "requestContext": {
            "elb": {
                "targetGroupArn": "arn:aws:elasticloadbalancing:us-east-1:XXXXXXXXXXX:targetgroup/sample/6d0ecf831eec9f09"
            }
        },
        "httpMethod": "GET",
        "path": "/",
        "queryStringParameters": {},
        "headers": {
            "accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
            "accept-encoding": "gzip",
            "accept-language": "en-US,en;q=0.5",
            "connection": "keep-alive",
            "cookie": "name=value",
            "host": "lambda-YYYYYYYY.elb.amazonaws.com",
            "upgrade-insecure-requests": "1",
            "user-agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:60.0) Gecko/20100101 Firefox/60.0",
            "x-amzn-trace-id": "Root=1-5bdb40ca-556d8b0c50dc66f0511bf520",
            "x-forwarded-for": "192.0.2.1",
            "x-forwarded-port": "80",
            "x-forwarded-proto": "http"
        },
        "body": "{\"i1\":10,\"i2\":5}",
        "isBase64Encoded": False
    }
    resp = Response(json.dumps(data))
    resp.headers['Lambda-Runtime-Aws-Request-Id'] = 'abcdefghijkl'
    return resp


@app.route('/2018-06-01/runtime/invocation/abcdefghijkl/response', methods=["POST"])
def handleResponse():
    req = request.get_data();
    print(req)
    return "ok"


if __name__ == '__main__':
    app.run()
