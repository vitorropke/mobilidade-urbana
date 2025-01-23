# Analysis of Urban Public Transportation User Accessibility in a Medium-Sized City Through Agent-Based Simulation Repository

This repository contains instance data and scripts for processing and visualizing the scenarios of the public transportation in Mossoró, Rio Grande do Norte, Brazil. The scenarios are from A to D where:

- A: Population demands public transportation every 1 hour.
- B: Population demands public transportation every 1 hour (peak hours).
- C: Population demands public transportation every 30 minutes.
- D: Population demands public transportation every 1 minute.

In all scenarios, the population begins to move from the extremes points of the city to the others, plus the center.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)

## Installation

It is recommended to use this repository in a virtual environment (venv) before installing the dependencies.

After configuring the venv, install the following dependencies:

- kaleido
- lxml
- matplotlib
- pandas
- plotly

## Usage

Some files and directories have numerical prefix meaning that they should be executed in the order they appear.

The MATSim version used for running the instance was Version 2024.0, released April 2024. The Java version used was 21 LTS. When installing another version of MATSim, make sure to install the correct Java version. The version of Python used was 3.10.

The `routes` directory contains 5 directories:

1. `0_instance`: Contains the instance of Mossoró, with the public transportation line with their routes, timetables, bus stops and vehicles. It also contains the 'config' and 'population' files for each scenario. The map was obtained using the MATSim plugin in the Java OpenStreetMap Editor (JOSM) application. The bus stops were obtained through the Google Maps, Cittamobi and Moovit applications. Additionally, the bus routes were obtained via a partnership with the city’s PT company, their social media information and in some applications aforementioned. The bus schedules for each route were also obtained from the company’s social media. Information about the vehicles’ characteristics was sourced from Ônibus Brasil (2020), while the population’s behavior was artificially created by an algorithm that generates pseudo-random routines, both in random places throughout the city and between the city’s extreme points. The script `population_generator.py` uses the `facilities_extreme_points.xml` to define the origins and destinations and then create the population for each scenario. The script generates 4 `population` files, one for each scenario. The directory also has 4 `config` files that are used by MATSim to perform the simulations.
2. `1_matsim_outputs`: Contains the output files of the MATSim simulations for each scenario.
3. `2_statistics`: Contains the statistics for each scenario. The script `statistics_calculator.py` generates the statistics files using the outputs from the `1_matsim_outputs` directory.
4. `3_indexes`: Contains the accessibility values of each route (combination of origin and destination) using the formulas of Allen et al. and Bracarense and Ferreira. Allen et al. is also used to calculate the accessibility of bus stops. The script `allen_calculator.py` uses data from `2_statistics` and `brac_and_ferr_calculator.py` from `1_matsim_outputs`.
5. `4_visualizations`: Contains the boxplots of the statistics and accessibility values. The script `boxplots_generator.py` uses data from `2_statistics` and from [`3_indexes/allen`, `3_indexes/brac_and_ferr`].

The `stops` directory contains 2 scripts:

1. `0_calculate_ingram`: Uses data from `od_cost_matrix.json` file that was generated from Google’s Distance Matrix API which computes distances between multiple origins and destinations using road paths. It calculates the Ingram's accessibility values and generates the `ingram_indexes.csv` file.
2. `1_generate_heatmap`: Uses data from `ingram_indexes.csv` file to generate the `heatmap.pdf` map.
