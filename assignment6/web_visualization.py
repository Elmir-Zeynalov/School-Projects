from temperature_CO2_plotter import plot_temperature, plot_CO2 				
from flask import Flask, render_template, url_for, request

#initialization 
app = Flask(__name__)
app.config['SEND_FILE_MAX_AGE_DEFAULT'] = 0
months = ['January', 'February', 'March', 'April', 'May', 'June', 'July',
			'August', 'September', 'October', 'November', 'December']
co2_countries_years = list(range(1960, 2017))
co2_c_list = []

#6.2
@app.route("/")
def front_page():
    return render_template('index.html')

#6.2
@app.route("/temperature")
def temperature_page(error=False):
	plot_temperature('January', show=False, save=True)
	return render_template('temperature.html', error=error, months=months)

#6.3
@app.route("/temperature_changed", methods=['POST'])
def temp_changed():
	month = request.form["months"]
	start_yr = int(request.form["year_from"])
	end_yr = int(request.form["year_to"])
	min_temp = int(request.form["min_temp"])
	max_temp = int(request.form["max_temp"])

	try:
		plot_temperature(month, time_range=[start_yr, end_yr],
						 temp_range=[min_temp, max_temp], show=False, save=True)
	except AssertionError as e:
		print(e)
		return temperature_page(error=True)

	return render_template('temperature.html', error=False, months=months)

#6.2
@app.route("/co2")
def co2_page(error=False):
	plot_CO2(show=False, save=True)
	return render_template('co2.html', error=error)

#6.3
@app.route("/co2_changed", methods=['POST'])
def co2_changed():
	start_yr = int(request.form["year_from"])
	end_yr = int(request.form["year_to"])
	min_co2 = int(request.form["min_co2"])
	max_co2 = int(request.form["max_co2"])

	try:
		plot_CO2(time_range=[start_yr, end_yr],
				 co2_range=[min_co2, max_co2],
				 show=False, save=True)
	except AssertionError as e:
		print(e)
		return co2_page(error=True)

	return render_template('co2.html', error=False)

#6.5
@app.route("/help")
def help():
	return render_template('help.html')

@app.after_request
def add_header(response):
    response.cache_control.no_store = True
    response.headers['Cache-Control'] = 'no-store, no-cache, must-revalidate,'+\
    									'post-check=0, pre-check=0, max-age=0'
    response.headers['Pragma'] = 'no-cache'
    response.headers['Expires'] = '-1'
    return response

if __name__ == "__main__":
    app.run(debug=False)
