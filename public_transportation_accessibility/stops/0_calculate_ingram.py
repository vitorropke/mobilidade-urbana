"""
This script calculates accessibility indices for bus stops based on an origin-destination (OD) cost matrix`and outputs
the results in a CSV file.

Key Features:
    - Reads an OD cost matrix from a JSON file (`od_cost_matrix.json`). Each origin contains a list of destinations
      with distance values in meters.
    - Computes the sum of distances for each origin, representing an accessibility index.
    - Outputs the results as a sorted CSV file (`ingram_indexes.csv`), where rows are sorted by the accessibility index
      in ascending order.

Equation:
    The accessibility index is computed using the following equation, as defined by Ingram (1971):

    \begin{equation}
        A_i = \sum_j^n a_{ij}
    \end{equation}

    Where:
    \begin{itemize}
        \item $A_i$: Integral accessibility at point $i$.
        \item $a_{ij}$: Relative accessibility between points $i$ and $j$, which can be measured, e.g., by distance.
        \item $n$: Total number of network points.
    \end{itemize}

Reference:
    Ingram, D., 1971. The concept of accessibility: A search for an operational form. Regional Studies 5, 101–107. URL:
    http://www.tandfonline.com/doi/abs/10.1080/09595237100185131, doi:10.1080/09595237100185131.

Input File:
    `od_cost_matrix.json`: A JSON file containing the following structure:
        [
            {
                "id": "BusStopID",
                "lat": Latitude,
                "lon": Longitude,
                "calculo": [
                    {
                        "distance": {"value": DistanceValue}
                    },
                    ...
                ]
            },
            ...
        ]

Output File:
    - `ingram_indexes.csv`: A CSV file containing the following columns:
        - `bus_stop`: The ID of the origin.
        - `latitude`: The latitude of the bus stop.
        - `longitude`: The longitude of the bus stop.
        - `accessibility`: The total sum of distances from the origin to all destinations.
"""
import csv
import json


def main() -> None:
    """
    Calculates accessibility indices for bus stops from an origin-destination cost matrix.

    Returns:
        None
    """
    with open(file='od_cost_matrix.json', mode='r') as file:
        data = json.load(fp=file)

    output: list[tuple[str, float, float, int]] = []

    for origin in data:
        distances_sum: int = 0
        for destination in origin['calculo']:
            distances_sum += destination['distance']['value']

        output.append((origin['id'], origin['lat'], origin['lon'], distances_sum))

    with open(file='ingram_indexes.csv', mode='w') as file:
        writer = csv.writer(file)
        writer.writerow(['stop', 'latitude', 'longitude', 'accessibility'])
        writer.writerows(sorted(output, key=lambda x: x[3]))


if __name__ == "__main__":
    main()
