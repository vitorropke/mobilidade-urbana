"""
This script calculates Allen et al.'s accessibility index for different public transportation scenarios based on
statistical travel time data.
It reads travel time data from CSV files, processes the data to compute accessibility indices, and outputs the results
to CSV files.

Key Features:
    - Calculates accessibility indices using the mean, median, and sum of travel times per route and stop.
    - Processes multiple scenarios and saves the results separately for each scenario.
    - Outputs the results in CSV format.

Equation:
    The accessibility index is computed using the following equation, as defined by Allen et al. (1993):

    \begin{equation}
        A_i = \frac{1}{n-1} \times \sum_j^n c_{ij}
    \end{equation}

    \begin{itemize}
        \item $A_i$: Accessibility of zone $i$.
        \item $c_{ij}$: Cost incurred to move between zones $i$ and $j$.
        \item $n$: Number of points used in the calculation.
    \end{itemize}

Reference:
    Allen, W., Liu, D., Singer, S., 1993. Accesibility measures of U.S. metropolitan areas. Transportation Research Part
    B: Methodological 27, 439–449. URL:https://linkinghub.elsevier.com/retrieve/pii/0191261593900164,
    doi:10.1016/0191-2615(93)90016-4.

Dependency:
    - `pandas`: Used for data manipulation and processing.

Input Files:
    - `scenario_a.csv`, `scenario_b.csv`, `scenario_c.csv` and `scenario_d.csv`: CSV files, for each scenario,
      containing the following columns:
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

Output Files:
    - `scenario_a.csv`, `scenario_b.csv`, `scenario_c.csv` and `scenario_d.csv` for the 'allen/by_route/' and
      'allen/by_stop/' directories, containing the following columns:
        - `route` or `stop`: The name of the route or the name of the bus stop, respectively.
        - `access_using_mean`: The accessibility value using the mean statistics.
        - `access_using_median`: The accessibility value using the median statistics.
        - `access_using_sum`: The accessibility value using the sum statistics.
"""
import pandas as pd
from pandas import DataFrame


def calculate_by_route(data: DataFrame, file_name: str) -> None:
    """
    Calculates Allen et al.'s accessibility index using travel times and saves the results to a CSV file.

    Args:
        data (DataFrame): The DataFrame containing travel time data with `trav_time_mean`, `trav_time_median`,
            `trav_time_sum` and `trip_count` columns.
        file_name (str): The file path where the output CSV will be saved.

    Returns:
        None
    """
    pd.DataFrame(data={
        'access_using_mean': data['trav_time_mean'] / data['trip_count'],
        'access_using_median': data['trav_time_median'] / data['trip_count'],
        'access_using_sum': data['trav_time_sum'] / data['trip_count']
    }).to_csv(path_or_buf=file_name)


def calculate_by_stop(data: DataFrame, file_name: str) -> None:
    """
    Splits travel time data by stop, calculates Allen et al.'s accessibility index, and saves the results to a CSV file.

    Args:
        data (DataFrame): The DataFrame containing travel time data indexed by stop.
        file_name (str): The file path where the output CSV will be saved.

    Returns:
        None
    """
    split_data: DataFrame = pd.DataFrame(data={
        'stop': data.index.to_series().str.split(pat=' ', expand=True)[1],
        'access_using_mean': data['trav_time_mean'],
        'access_using_median': data['trav_time_median'],
        'access_using_sum': data['trav_time_sum']
    })

    grouped_data: DataFrame = split_data.groupby(by='stop').sum()

    grouped_data = grouped_data / (grouped_data.shape[0] - 1)

    grouped_data.to_csv(path_or_buf=file_name)


def main() -> None:
    """
    Reads travel time data from CSV files for multiple scenarios, processes the data, and saves the results to CSV
    files.

    Returns:
        None
    """
    selected_columns: list[str] = ['route', 'trav_time_mean', 'trav_time_median', 'trav_time_sum', 'trip_count']
    scenario_a: DataFrame = pd.read_csv(filepath_or_buffer='../2_statistics/scenario_a.csv', index_col='route',
                                        usecols=selected_columns)
    scenario_b: DataFrame = pd.read_csv(filepath_or_buffer='../2_statistics/scenario_b.csv', index_col='route',
                                        usecols=selected_columns)
    scenario_c: DataFrame = pd.read_csv(filepath_or_buffer='../2_statistics/scenario_c.csv', index_col='route',
                                        usecols=selected_columns)
    scenario_d: DataFrame = pd.read_csv(filepath_or_buffer='../2_statistics/scenario_d.csv', index_col='route',
                                        usecols=selected_columns)

    calculate_by_route(data=scenario_a, file_name='allen/by_route/scenario_a.csv')
    calculate_by_route(data=scenario_b, file_name='allen/by_route/scenario_b.csv')
    calculate_by_route(data=scenario_c, file_name='allen/by_route/scenario_c.csv')
    calculate_by_route(data=scenario_d, file_name='allen/by_route/scenario_d.csv')

    calculate_by_stop(data=scenario_a, file_name='allen/by_stop/scenario_a.csv')
    calculate_by_stop(data=scenario_b, file_name='allen/by_stop/scenario_b.csv')
    calculate_by_stop(data=scenario_c, file_name='allen/by_stop/scenario_c.csv')
    calculate_by_stop(data=scenario_d, file_name='allen/by_stop/scenario_d.csv')


if __name__ == "__main__":
    main()
