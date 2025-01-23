"""
This script calculates Bracarense and Ferreira's accessibility indices for public transportation trips based on travel
times. It processes trip data from MATSim, filters out irrelevant trips, and calculates an accessibility score for each
route and outputs the results to CSV files.

Key Features:
    - Calculates accessibility indices using the travel time per route.
    - Processes multiple scenarios and saves the results separately for each scenario.
    - Outputs the results in CSV format.

Equation:
    The accessibility index is computed using the following equation, as defined by Bracarense and Ferreira (2018):

    \begin{equation}
        A_{ij} = \sum_j^n \left(1 - \frac{T_{ij} - T_{min}}{T_{max} - T_{min}}\right)
    \end{equation}

    \begin{itemize}
        \item $A_{ij}$: Accessibility index from node $i$ to destinations $j$.
        \item $T_{ij}$: Trip time from node $i$ to destination $j$, in minutes.
        \item $T_{max}$: Maximum trip time from $i$ to $j$ for which the trip can be considered feasible for the user,
            in minutes.
        \item $T_{min}$: Shortest trip time from $i$ to $j$ found in the analyzed transportation network, in minutes.
        \item $n$: Number of key destinations of type $j$ for which the accessibility of node $i$ is analyzed.
    \end{itemize}

Reference:
    Bracarense, L.d.S.F.P., Ferreira, J.O.N., 2018. Índice de acessibilidade para comparação dos modos de transporte
    privado e coletivo. urbe. Revista Brasileira de Gestão Urbana 10, 600–613.
    URL: http://www.scielo.br/scielo.php?script=sci_arttext&pid=S2175-33692018000300600&lng=pt&tlng=pt,
    doi:10.1590/2175-3369.010.003.ao08.

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

Output Files:
    - `scenario_a.csv`, `scenario_b.csv`, `scenario_c.csv` and `scenario_d.csv` containing the following columns:
        - `route`: The name of the route.
        - `accessibility`: The accessibility value of the route.
"""
import pandas as pd
from pandas import DataFrame


def calculate_accessibility(group_data: DataFrame) -> float:
    """
    Calculates Bracarense and Ferreira's accessibility score for a group of trips.

    The accessibility score is computed by normalizing the travel times and summing the
    inverse of the normalized values. A lower travel time contributes to a higher accessibility score.

    Args:
        group_data (DataFrame): The DataFrame containing travel time data for a group of trips.

    Returns:
        float: The calculated accessibility score.
    """
    t_min: float = group_data['trav_time'].min()
    t_max: float = group_data['trav_time'].max()

    if t_min == t_max:
        return 1.0

    return (1.0 - ((group_data['trav_time'] - t_min) / (t_max - t_min))).sum()


def process_and_calculate(data: DataFrame, file_name: str) -> None:
    """
    Process the trip data and calculate the accessibility scores for each person.

    This function filters out walk-only trips, converts travel times from 'hh:mm:ss' to minutes,
    removes trips longer than 2 hours, and calculates accessibility scores for each person.
    The results are saved to a CSV file.

    Args:
        data (DataFrame): The DataFrame containing trip data.
        file_name (str): The file path where the output CSV will be saved.

    Returns:
        None
    """
    # Remove all walk-only trips.
    filtered_data = data[data['modes'] != 'walk'].drop(columns='modes').reset_index(drop=True)

    # hh:mm:ss to minutes.
    filtered_data['trav_time'] = pd.to_timedelta(arg=filtered_data['trav_time']).dt.total_seconds() / 60

    # Remove 25% of the longest trips
    # filtered_data = filtered_data[filtered_data['trav_time'] < filtered_data['trav_time'].quantile(0.75)].reset_index(
    #     drop=True)

    # Only consider trips with less than 1:30 hour long.
    filtered_data = filtered_data[filtered_data['trav_time'] < 90].reset_index(drop=True)

    # Remove the time component ' hh:mm:ss' from the person id.
    filtered_data['person'] = filtered_data['person'].str[:-9]

    # Calculate and save.
    filtered_data[['person', 'trav_time']].groupby(by='person').apply(func=calculate_accessibility,
                                                                      include_groups=False).rename(
        index='accessibility').to_csv(path_or_buf=file_name, index_label='route')


def main() -> None:
    """
    Reads the `output_trips.csv` files for each scenario and calls the processing and calculating function.

    Returns:
        None
    """
    selected_columns: list[str] = ['person', 'trav_time', 'modes']
    scenario_a: DataFrame = pd.read_csv(filepath_or_buffer='../1_matsim_outputs/scenario_a/output_trips.csv.gz',
                                        sep=';', usecols=selected_columns)
    scenario_b: DataFrame = pd.read_csv(filepath_or_buffer='../1_matsim_outputs/scenario_b/output_trips.csv.gz',
                                        sep=';', usecols=selected_columns)
    scenario_c: DataFrame = pd.read_csv(filepath_or_buffer='../1_matsim_outputs/scenario_c/output_trips.csv.gz',
                                        sep=';', usecols=selected_columns)
    scenario_d: DataFrame = pd.read_csv(filepath_or_buffer='../1_matsim_outputs/scenario_d/output_trips.csv.gz',
                                        sep=';', usecols=selected_columns)

    process_and_calculate(data=scenario_a, file_name='brac_and_ferr/scenario_a.csv')
    process_and_calculate(data=scenario_b, file_name='brac_and_ferr/scenario_b.csv')
    process_and_calculate(data=scenario_c, file_name='brac_and_ferr/scenario_c.csv')
    process_and_calculate(data=scenario_d, file_name='brac_and_ferr/scenario_d.csv')


if __name__ == "__main__":
    main()
