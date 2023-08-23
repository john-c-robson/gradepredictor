
from flask import Flask
from flask import request
import json
import numpy as np
from sklearn.linear_model import LinearRegression
import matplotlib.pyplot as plt
from urllib import parse


app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Try /example ;)'


@app.route('/example')
def handle_request():
    #text = str(request.args.get('input'))
    #character_count = len(text)


    x = np.array([1,2,3,4,5,6,7,8,9,10,11,12]).reshape((-1, 1))
    y = np.array([72,64,78,66,68,73,66,87,88,69,73,75]).reshape((-1, 1))

    nextgrade, prediction = make_graph(x,y)
    a = np.array(nextgrade,int)
    aa = a.tolist()
    b = np.array(prediction,int)
    bb = b.tolist()

    data_set = {'guess': aa[0], 'prediction': bb}
    print(data_set)
    json_dump = json.dumps(data_set)


    return json_dump

def make_graph(x, y):

    model = LinearRegression()

    model.fit(x,y)

    prediction = model.predict(x)

    print(f"intercept: {model.intercept_}")
    print(f"slope: {model.coef_}")
    size = len(x)

    test = np.array([size+1]).reshape((-1, 1))

    nextgrade = model.predict(test)

    return nextgrade, prediction

