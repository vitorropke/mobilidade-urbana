"""
This script processes trip data from multiple transportation scenarios and generates statistical summaries for each
scenario. The summaries include mean, median, sum, maximum, and minimum travel and wait times, along with the number of
trips per route. The results are saved as CSV files.

Key Features:
    - Process trip data and generates statistical summaries for transportation scenarios.
    - Calculates the mean, median, sum, maximum and minimum travel and wait times and trips per route.
    - Process scenarios A, B, C and D.
    - Outputs the statistics into CSV files for each scenario.

Dependency:
    - `pandas`: Used for data manipulation and processing.

Input Files:
    - `output_trips.csv.gz`: A compressed CSV file coming from MATSim, for each scenario, containing the following
      columns:
        - `person`: The person id.
        - `trip_number`: The trip number the person is taking.
        - `trip_id`: The trip id which is just a concatenation of `person` with `trip_number`.
        - `dep_time`: The person departure time.
        - `trav_time`: The time spent traveling, which also includes the `wait_time`.
        - `wait_time`: The time spent waiting for the public transportation.
        - `traveled_distance`: The distance traveled on the map.
        - `euclidean_distance`: The distance traveled in a straight line.
        - `main_mode` and `longest_distance_mode`: The mode with the longest distance traveled.
        - `modes`: Transportation modes used during the trip.
        - `start_activity_type`: The origin type. E.g.: `home`, `work`, `shop`, etc.
        - `end_activity_type`: The destination type. E.g.: `home`, `work`, `shop`, etc.
        - `start_facility_id`: The facility id if it is the origin of the trip.
        - `start_link`: The street id where the person departs.
        - `start_x`: The origin x coordinates.
        - `start_y`: The origin y coordinates.
        - `end_facility_id`: The facility id if it is the destination of the trip.
        - `end_link`: The street id where the person arrives.
        - `end_x`: The destination x coordinates.
        - `end_y`: The destination y coordinates.
        - `first_pt_boarding_stop`: First boarding point.
        - `last_pt_egress_stop`: Last egress point.

Output File:
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
"""
import pandas as pd
from pandas import DataFrame, Series


def process_and_save(data: DataFrame, file_name: str) -> None:
    """
    Processes trip data and saves statistical summaries to a CSV file.

    This function performs the following steps:
        1. Filters out walk-only trips and unnecessary columns.
        2. Converts travel and wait times to minutes.
        3. Computes mean, median, sum, maximum, and minimum values for travel and wait times, grouped by person.
        4. Counts the number of trips per route.
        5. Concatenates the results into a single DataFrame.
        6. (Optional) Formats time-related columns as `hh:mm:ss`.
        7. Saves the processed DataFrame to a CSV file.

    Args:
        data (DataFrame): The DataFrame containing trip data. Must include the columns:
            - `person`: Identifier for each individual.
            - `dep_time`: Departure time of the trip.
            - `trav_time`: Total travel time.
            - `wait_time`: Total waiting time.
            - `modes`: Modes of transportation used in the trip.
        file_name (str): The file path where the output CSV will be saved.

    Returns:
        None
    """
    # Remove all walk-only trips.
    filtered_data = data[data['modes'] != 'walk'].drop(columns='modes').reset_index(drop=True)

    # hh:mm:ss to minutes.
    filtered_data['trav_time'] = pd.to_timedelta(arg=filtered_data['trav_time']).dt.total_seconds() / 60
    filtered_data['wait_time'] = pd.to_timedelta(arg=filtered_data['wait_time']).dt.total_seconds() / 60

    # Remove 25% of the longest trips
    # filtered_data = filtered_data[filtered_data['trav_time'] < filtered_data['trav_time'].quantile(0.75)].reset_index(
    #     drop=True)

    # Only consider trips with less than 1:30 hour long.
    filtered_data = filtered_data[filtered_data['trav_time'] < 90].reset_index(drop=True)

    # Remove the time component ' hh:mm:ss' from the person id.
    filtered_data['person'] = filtered_data['person'].str[:-9]

    # Calculate.
    mean_data: DataFrame = filtered_data[['person', 'trav_time', 'wait_time']].groupby(by='person').mean().rename(
        columns={'trav_time': 'trav_time_mean', 'wait_time': 'wait_time_mean'})

    median_data: DataFrame = filtered_data[['person', 'trav_time', 'wait_time']].groupby(by='person').median().rename(
        columns={'trav_time': 'trav_time_median', 'wait_time': 'wait_time_median'})

    sum_data: DataFrame = filtered_data[['person', 'trav_time', 'wait_time']].groupby(by='person').sum().rename(
        columns={'trav_time': 'trav_time_sum', 'wait_time': 'wait_time_sum'})

    max_trav_time_data: DataFrame = filtered_data[['person', 'dep_time', 'trav_time']].loc[
        filtered_data.groupby(by='person')['trav_time'].idxmax()].set_index(keys='person').rename(
        columns={'dep_time': 'max_trav_time_dep_time', 'trav_time': 'max_trav_time'})

    min_trav_time_data: DataFrame = filtered_data[['person', 'dep_time', 'trav_time']].loc[
        filtered_data.groupby(by='person')['trav_time'].idxmin()].set_index(keys='person').rename(
        columns={'dep_time': 'min_trav_time_dep_time', 'trav_time': 'min_trav_time'})

    max_wait_time_data: DataFrame = filtered_data[['person', 'dep_time', 'wait_time']].loc[
        filtered_data.groupby(by='person')['wait_time'].idxmax()].set_index(keys='person').rename(
        columns={'dep_time': 'max_wait_time_dep_time', 'wait_time': 'max_wait_time'})

    min_wait_time_data: DataFrame = filtered_data[['person', 'dep_time', 'wait_time']].loc[
        filtered_data.groupby(by='person')['wait_time'].idxmin()].set_index(keys='person').rename(
        columns={'dep_time': 'min_wait_time_dep_time', 'wait_time': 'min_wait_time'})

    trip_count_data: Series = filtered_data.groupby(by='person').size().rename(index='trip_count')

    # Concat and save.
    output_df: DataFrame = pd.concat(
        objs=[mean_data, median_data, sum_data, max_trav_time_data, min_trav_time_data, max_wait_time_data,
              min_wait_time_data, trip_count_data], axis=1)

    # columns_to_convert_from_seconds_to_text: list[str] = ['trav_time_mean', 'wait_time_mean',
    #                                                       'trav_time_median', 'wait_time_median',
    #                                                       'max_trav_time', 'min_trav_time',
    #                                                       'max_wait_time', 'min_wait_time']
    # for column in columns_to_convert_from_seconds_to_text:
    #     output_df[column] = pd.to_timedelta(arg=output_df[column], unit='minute').dt.components.apply(
    #         lambda x: f"{x.hours:02}:{x.minutes:02}:{x.seconds:02}", axis=1)

    output_df.to_csv(path_or_buf=file_name, index_label='route')


def main() -> None:
    """
    Reads the `output_trips.csv` files for each scenario and calls the processing and saving function.

    Returns:
        None
    """
    selected_columns: list[str] = ['person', 'dep_time', 'trav_time', 'wait_time', 'modes']
    scenario_a: DataFrame = pd.read_csv(filepath_or_buffer='../1_matsim_outputs/scenario_a/output_trips.csv.gz',
                                        sep=';', usecols=selected_columns)
    scenario_b: DataFrame = pd.read_csv(filepath_or_buffer='../1_matsim_outputs/scenario_b/output_trips.csv.gz',
                                        sep=';', usecols=selected_columns)
    scenario_c: DataFrame = pd.read_csv(filepath_or_buffer='../1_matsim_outputs/scenario_c/output_trips.csv.gz',
                                        sep=';', usecols=selected_columns)
    scenario_d: DataFrame = pd.read_csv(filepath_or_buffer='../1_matsim_outputs/scenario_d/output_trips.csv.gz',
                                        sep=';', usecols=selected_columns)

    process_and_save(data=scenario_a, file_name='scenario_a.csv')
    process_and_save(data=scenario_b, file_name='scenario_b.csv')
    process_and_save(data=scenario_c, file_name='scenario_c.csv')
    process_and_save(data=scenario_d, file_name='scenario_d.csv')


if __name__ == "__main__":
    main()
