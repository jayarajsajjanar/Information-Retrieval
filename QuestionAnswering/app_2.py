from flask import Flask, render_template, request, url_for
import urllib, json
import os
app = Flask(__name__)

# @app.route("/")
# def main():
#     # return "Welcome!"
#     return render_template('first.html')

@app.route('/showSignUp')
def showSignUp():
    return render_template('signup.html')

# @app.route('/signUp')
# def signUp():
#     # create user code will be here !!
# @app.route('/signUp',methods=['POST', 'GET'])
# def signUp():
#     # create user code will be here !!    
#     # read the posted values from the UI
#     if request.method=='Post':
# 	    _name = request.form['inputName']
# 	    _email = request.form['inputEmail']
# 	    _password = request.form['inputPassword']

#     # # validate the received values
# 	   #  if _name and _email and _password:
# 	   #      return json.dumps({'html':'<span>All fields good !!</span>'})
# 	   #  else:
# 	   #      return json.dumps({'html':'<span>Enter the required fields</span>'})
# 	return render_template('signup.html')

# @app.route('/login', methods=['POST', 'GET'])
# def login():
#     error = None
#     if request.method == 'POST':
#         if valid_login(request.form['username'],
#                        request.form['password']):
#             return log_the_user_in(request.form['username'])
#         else:
#             error = 'Invalid username/password'
#     # the code below is executed if the request method
#     # was GET or the credentials were invalid
#     return render_template('signup.html', error=error)

@app.route('/')
def form():
    return render_template('form_submit.html')

# Define a route for the action of the form, for example '/hello/'
# We are also defining which type of requests this route is 
# accepting: POST requests in this case
@app.route('/', methods=['POST'])
def hello():
    query=request.form['query']
    url = "http://ec2-54-244-12-59.us-west-2.compute.amazonaws.com:8983/solr/first/select?indent=on&q="+query+"&rows=1&wt=json"
    response = urllib.urlopen(url)
    data = json.loads(response.read())
    results = data['response']['docs'][0]['text_en']
    return render_template('form_action.html', query=query, query_results=results)

@app.route("/trial")
def trial():
    return render_template('first.html')
    # return render_template('form_action.html', query="hi", query_results="hi")

@app.route("/trial", methods=['POST'])
def trial_output():
	query=request.form['query']
	return render_template('form_action.html', query=query, query_results="hi")# return render_template('form_action.html', query="hi", query_results="hi")
	# url = "http://ec2-54-244-12-59.us-west-2.compute.amazonaws.com:8983/solr/first/select?indent=on&q="+query+"&rows=1&wt=json"
	# response = urllib.urlopen(url)
 #    data = json.loads(response.read())
 #    results = data['response']['docs'][0]['text_en']
 #    return render_template('form_action.html', query=query, query_results="jjjjjjjj")

@app.route("/report")
def report():
    return render_template('report.html')

if __name__ == "__main__":
	port = int(os.environ.get("PORT", 5000))
	app.run(host='0.0.0.0', port=port)#simply changing
