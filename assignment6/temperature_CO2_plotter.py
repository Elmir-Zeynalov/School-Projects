import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import os

"""
Documentation for temperature_CO2_plotter.py.
"""

#6.1
def plot_temperature(month, time_range=[1816, 2012], temp_range=[-6, 23],show=True, save=False):
	"""Method that creates a plot for a specific month.

	Given a specific month, time span and temperature range a plot is constructed.
	
	Arguments:
		month {String} -- the month you want to generate a plot for
	
	Keyword Arguments:
		time_range {list} -- min and max year (default: {[1816, 2012]})
		temp_range {list} -- min and max temperature (default: {[-6, 23]})
		show {bool} -- to show plot (default: {True})
		save {bool} -- to save plot as png (default: {False})
	"""
	try:
		df = pd.read_csv("temperature.csv", sep=',')
		temps = df[month]
	except KeyError:
		print("plot_temperature() error: month must be on format 'January',",
				"'February', ..., 'December'.\n")
		return
	except OSError:
		print("plot_temperature() error: temperature.csv must be in working",
				"directory.\n")
		return

	years = df['Year']
	plt.rcParams['axes.facecolor'] = '#FFF4EE'
	plt.plot(years, temps, color='#5370BB')
	plt.xlabel('Year')
	plt.ylabel('Temperature')
	plt.title("Temperature for {} from {} to {}.".format(month, time_range[0],
														 time_range[1]))
	plt.ylim(temp_range[0], temp_range[1])
	plt.xlim(time_range[0], time_range[1])
	plt.grid(zorder=0)

	if save:
		if not os.path.exists('static'):
			os.makedirs('static')

		plt.savefig('static/temperature_plot.jpg')

	if show:
		plt.show()

	plt.close()

#6.1
def plot_CO2(time_range=[1751, 2012], co2_range=[3, 9671], show=True,save=False):
	"""Creates a plot of CO2 levels.
	
	Given a time span as a list and a co2 range this method creates a plot over
	the given time span and co2 range. 
	
	Keyword Arguments:
		time_range {list} -- min and max value for years (default: {[1751, 2012]})
		co2_range {list} -- min and max value of CO2 level (default: {[3, 9671]})
		show {bool} -- show plot (default: {True})
		save {bool} -- save plot (default: {False})
	"""
	df = pd.read_csv("co2.csv", sep=',')

	co2 = df['Carbon']
	years = df['Year']
	plt.rcParams['axes.facecolor'] = '#D2D2D2'
	plt.plot(years, co2, color='#3D2200')
	plt.xlabel('Year')
	plt.ylabel('CO2')
	plt.title("Earth's CO2 levels from {} to {}.".format(time_range[0],
														 time_range[1]))
	plt.ylim(co2_range[0], co2_range[1])
	plt.xlim(time_range[0], time_range[1])
	plt.grid(zorder=0)

	if save:
		if not os.path.exists('static'):
			os.makedirs('static')
		plt.savefig('static/co2_plot.jpg')

	if show:
		plt.show()

	plt.close()


if __name__ == "__main__":
	plot_temperature('March', show=True)
	plot_CO2(show=False)
