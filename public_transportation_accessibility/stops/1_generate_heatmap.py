"""
This script generates a heatmap using geographic scatter plot visualization from accessibility data.
The data is read from a CSV file containing geographical coordinates and accessibility indices.
The resulting heatmap is saved as a PDF file.

Key Features:
    - The map uses `carto-positron-nolabels` style for the base map.
    - Colors represent the accessibility indices using the `Plasma` color scale.
    - The map centers around a specific location with customizable zoom.
    - The layout is styled with minimal margins and a specific font family and size.

Dependencies:
    - `pandas`: Used for data manipulation and processing.
    - `plotly`: Used for creating the scatter map visualization.
    - `kaleido`: Used for exporting plotly figures as static images, generating PDF and other image formats.

Input File:
    - `ingram_indexes.csv`: A CSV file containing the following columns:
        - `latitude`: The latitude of each data point.
        - `longitude`: The longitude of each data point.
        - `accessibility`: The accessibility index value for each location.

Output File:
    - `heatmap.pdf`: A PDF file containing the heatmap visualization.
"""
import pandas as pd
import plotly.express as px
from pandas import DataFrame
from plotly.graph_objs import Figure


def main() -> None:
    """
    Generates a heatmap from accessibility data and saves it as a PDF file.

    Returns:
        None
    """
    indexes: DataFrame = pd.read_csv('ingram_indexes.csv')

    fig: Figure = px.scatter_map(data_frame=indexes, lat='latitude', lon='longitude', color='accessibility',
                                 labels={'accessibility': 'Accessibility'},
                                 color_continuous_scale=px.colors.sequential.Plasma, zoom=11.34,
                                 center={'lat': -5.181, 'lon': -37.34}, map_style='carto-positron-nolabels')

    fig.update_layout(margin=dict(l=0, r=0, t=0, b=0), font_family='Times New Roman', font_size=7)

    fig.write_image('heatmap.pdf')


if __name__ == "__main__":
    main()
