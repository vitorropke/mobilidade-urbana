"""
This script provides functions to read CSV files containing accessibility and travel statistics for different
transportation scenarios, process the data, and generate boxplot visualizations. It is designed to handle four
scenarios (A, B, C, D) and create plots comparing accessibility metrics and travel times across these scenarios.

Key Features:
    - Plots the data from Allen et al.'s index for each scenario.
    - Plots the data from Bracarense and Ferreira's index for each scenario.
    - Plots the statistics for each scenario.

Dependencies:
    - `matplotlib`: Used for plotting and saving the visualizations.
    - `pandas`: Used for data manipulation and processing.

Input Files:
    - `scenario_a.csv`, `scenario_b.csv`, `scenario_c.csv` and `scenario_d.csv` from `../2_statistics` directory: CSV
      files, for each scenario, containing the following columns:
        - `route`: The name of the route.
        - `trav_time_mean`: The mean travel time of the route.
        - `wait_time_mean`: The mean wait time of the route.
        - `trav_time_median`: The median travel time of the route.
        - `wait_time_median`: The median wait time of the route.
        - `trav_time_sum`: The sum of all travel times of the route.
        - `wait_time_sum`: The sum of all waiting times of the route.
        - `max_trav_time_dep_time`: The departure time at which the maximum travel time of the route occurred.
        - `max_trav_time`: The maximum travel time of the route.
        - `min_trav_time_dep_time`: The departure time at which the minimum travel time of the route occurred.
        - `min_trav_time`: The minimum travel time of the route.
        - `max_trav_time_wait_time`: The departure time at which the maximum wait time of the route occurred.
        - `max_wait_time`: The maximum wait time of the route.
        - `min_trav_time_wait_time`: The departure time at which the minimum wait time of the route occurred.
        - `min_wait_time`: The minimum wait time of the route.
        - `trip_count`: The total number of trips of the route.
    - `scenario_a.csv`, `scenario_b.csv`, `scenario_c.csv` and `scenario_d.csv` from `../3_indexes/allen/by_route` and
      `../3_indexes/allen/by_stop`directories: CSV files, for each scenario, containing the following columns:
        - `route` or `stop`: The name of the route or the name of the bus stop, respectively.
        - `access_using_mean`: The accessibility value using the mean statistics.
        - `access_using_median`: The accessibility value using the median statistics.
        - `access_using_sum`: The accessibility value using the sum statistics.
    - `scenario_a.csv`, `scenario_b.csv`, `scenario_c.csv` and `scenario_d.csv` from `../3_indexes/brac_and_ferr`
      directory: CSV files, for each scenario, containing the following columns:
        - `route`: The name of the route.
        - `accessibility`: The accessibility value of the route.

Output Files:
    - `allen_boxplot.pdf`: Contains the boxplots of the four scenarios grouped by mean, median and sum, which are also
      grouped by route and stop.
    - `brac_and_ferr_boxplot.pdf`: Contains the boxplots of the four scenarios.
    - `statistics_boxplot.pdf`: Contains the boxplots of the statistics (`Average trip`, `Median trip`, `Average wait`,
      `Median wait`, `Longest trip`, `Shortest trip`, `Longest wait`, `Shortest wait`) grouped by the scenarios.
"""
import matplotlib
import matplotlib.pyplot as plt
import pandas as pd
from pandas import DataFrame

matplotlib.rcParams['font.family'] = ['Times New Roman']
matplotlib.rcParams['font.size'] = 7


def read_csvs(filepath: str, index: str, selected_columns: list[str] = None) -> list[DataFrame]:
    """
    Reads CSV files for different scenarios and returns them as a list of DataFrames.

    Args:
        filepath (str): The directory path containing the scenario CSV files.
        index (str): The column to set as the index for the DataFrame.
        selected_columns (list[str], optional): Specific columns to read from the CSV files.

    Returns:
        list[DataFrame]: List containing DataFrames for scenarios A, B, C and D.
    """
    scenario_a: DataFrame = pd.read_csv(filepath_or_buffer=f'{filepath}/scenario_a.csv', index_col=index,
                                        usecols=selected_columns)
    scenario_b: DataFrame = pd.read_csv(filepath_or_buffer=f'{filepath}/scenario_b.csv', index_col=index,
                                        usecols=selected_columns)
    scenario_c: DataFrame = pd.read_csv(filepath_or_buffer=f'{filepath}/scenario_c.csv', index_col=index,
                                        usecols=selected_columns)
    scenario_d: DataFrame = pd.read_csv(filepath_or_buffer=f'{filepath}/scenario_d.csv', index_col=index,
                                        usecols=selected_columns)

    return [scenario_a, scenario_b, scenario_c, scenario_d]


def plot_allen() -> None:
    """
    Generates boxplots for accessibility metrics using data from the `allen` directory.
    Saves the plot as `allen_boxplot.pdf`.

    Returns:
        None
    """
    axs = plt.subplots(nrows=2, ncols=3)[1]
    scenario_labels: list[str] = ['Scenario A', 'Scenario B', 'Scenario C', 'Scenario D']

    filepaths: list[str] = ['by_route', 'by_stop']
    indexes: list[str] = ['route', 'stop']
    columns: list[str] = ['access_using_mean', 'access_using_median', 'access_using_sum']
    titles: list[list[str]] = [['mean', 'median', 'sum'], ['by route', 'by stop']]
    for i in range(2):
        scenarios: list[DataFrame] = read_csvs(filepath=f'../3_indexes/allen/{filepaths[i]}', index=indexes[i])
        for j in range(3):
            axs[i, j].boxplot(x=[scenario[columns[j]] for scenario in scenarios], orientation='horizontal',
                              tick_labels=scenario_labels)
            axs[i, j].invert_yaxis()
            axs[i, j].set_title(label=f'Accessibility using {titles[0][j]} {titles[1][i]}')

    # Hide y-axis tick labels for the 2nd, 3rd, 5th and 6th subplots.
    axs[0, 1].set_yticklabels(labels=[])
    axs[0, 2].set_yticklabels(labels=[])
    axs[1, 0].set_xlabel(xlabel='Accessibility')
    axs[1, 1].set(xlabel='Accessibility', yticklabels=[])
    axs[1, 2].set(xlabel='Accessibility', yticklabels=[])

    plt.savefig(fname='allen_boxplot.pdf', transparent=True, bbox_inches='tight')


def plot_brac_and_ferr() -> None:
    """
    Generates boxplots for accessibility metrics using data from the `brac_and_ferr` directory.
    Saves the plot as `brac_and_ferr_boxplot.pdf`.

    Returns:
        None
    """
    plt.figure()

    scenarios: list[DataFrame] = read_csvs(filepath='../3_indexes/brac_and_ferr', index='route')
    scenario_labels: list[str] = ['Scenario A', 'Scenario B', 'Scenario C', 'Scenario D']
    plt.boxplot(x=[scenario['accessibility'] for scenario in scenarios], orientation='horizontal',
                tick_labels=scenario_labels)
    plt.gca().invert_yaxis()
    plt.xlabel(xlabel='Accessibility')
    maximum_limit: float = 100.0
    minimum_limit: float = -0.05 * maximum_limit
    plt.xlim(minimum_limit, maximum_limit)

    plt.savefig(fname='brac_and_ferr_boxplot.pdf', transparent=True, bbox_inches='tight')


def time_to_minutes(data: DataFrame) -> None:
    """
    Converts time-related columns in a DataFrame from timedelta to total minutes, if the column values has the format
    `hh:mm:ss`.

    Args:
        data (DataFrame): The DataFrame containing time-related columns.

    Returns:
        None
    """
    try:
        # Regular expression for 'hh:mm:ss' format
        time_format: str = r'^\d{2}:\d{2}:\d{2}$'

        # Check if all selected columns have all values matching the time format
        selected_columns: list[str] = ['trav_time_mean', 'trav_time_median',
                                       'wait_time_mean', 'wait_time_median',
                                       'max_trav_time', 'min_trav_time',
                                       'max_wait_time', 'min_wait_time']

        if data[selected_columns].apply(lambda col: col.str.match(time_format).all()):
            data['trav_time_mean'] = pd.to_timedelta(arg=data['trav_time_mean']).dt.total_seconds() / 60
            data['trav_time_median'] = pd.to_timedelta(arg=data['trav_time_median']).dt.total_seconds() / 60
            data['wait_time_mean'] = pd.to_timedelta(arg=data['wait_time_mean']).dt.total_seconds() / 60
            data['wait_time_median'] = pd.to_timedelta(arg=data['wait_time_median']).dt.total_seconds() / 60
            data['max_trav_time'] = pd.to_timedelta(arg=data['max_trav_time']).dt.total_seconds() / 60
            data['min_trav_time'] = pd.to_timedelta(arg=data['min_trav_time']).dt.total_seconds() / 60
            data['max_wait_time'] = pd.to_timedelta(arg=data['max_wait_time']).dt.total_seconds() / 60
            data['min_wait_time'] = pd.to_timedelta(arg=data['min_wait_time']).dt.total_seconds() / 60
    except AttributeError:
        pass


def plot_statistics() -> None:
    """
    Generates boxplots for various travel and wait time statistics across different scenarios.
    Saves the plot as `statistics_boxplot.pdf`.

    Returns:
        None
    """
    selected_columns: list[str] = ['route', 'trav_time_mean', 'trav_time_median', 'wait_time_mean', 'wait_time_median',
                                   'max_trav_time', 'min_trav_time', 'max_wait_time', 'min_wait_time']
    scenarios: list[DataFrame] = read_csvs(filepath='../2_statistics', index='route', selected_columns=selected_columns)

    for i in range(len(scenarios)):
        scenarios[i] = scenarios[i][selected_columns[1:]]
        time_to_minutes(data=scenarios[i])

    axs = plt.subplots(nrows=2, ncols=2)[1]
    statistics_labels: list[str] = ['Average trip', 'Median trip', 'Average wait', 'Median wait', 'Longest trip',
                                    'Shortest trip', 'Longest wait', 'Shortest wait']
    maximum_value: float = pd.concat(objs=scenarios).max().max()

    titles: list[str] = ['Scenario A', 'Scenario B', 'Scenario C', 'Scenario D']
    index: int = 0
    for i in range(2):
        for j in range(2):
            axs[i, j].boxplot(x=scenarios[index], orientation='horizontal', tick_labels=statistics_labels)
            axs[i, j].set(title=titles[index], xlim=(-0.05 * maximum_value, maximum_value * 1.05))
            index += 1

    for ax in axs.flat:
        ax.set_xlabel(xlabel='Time in minutes')
        ax.label_outer()

    plt.savefig(fname='statistics_boxplot.pdf', transparent=True, bbox_inches='tight')


def main() -> None:
    """
    Main function that calls other plotting functions to generate and save the plots.

    Returns:
        None
    """
    plot_allen()
    plot_brac_and_ferr()
    plot_statistics()


if __name__ == "__main__":
    main()
