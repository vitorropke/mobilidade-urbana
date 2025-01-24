from fastkml import kml
from shapely.geometry import LineString
import plotly.graph_objects as go
import pygeoif

with open('SISTEMA ATUAL - 2020.kml', 'rb') as file:
    input_file = file.read()

# Parse the KML file
k = kml.KML()
k.from_string(input_file)

# Extract the routes (geometry)
features = list(k.features())
placemarks = list(features[0].features())  # Assuming there's one document

routes = []
for placemark in placemarks:
    geometry = placemark.geometry
    if isinstance(geometry, LineString):
        routes.append(geometry.coords)
    elif isinstance(geometry, pygeoif.geometry.LineString):
        # Convert pygeoif LineString to Shapely LineString
        shapely_linestring = LineString(geometry.coords)
        routes.append(shapely_linestring.coords)
    else:
        print(f"Unhandled geometry type: {type(geometry)}")

# Create a figure for the map
fig = go.Figure()

# Ensure there are routes before proceeding
if routes:
    # Use the first route to define the map's center
    first_route = routes[0]
    first_latitude = first_route[0][1]
    first_longitude = first_route[0][0]
else:
    raise ValueError("No routes found in the KML file.")

route_colors = [
    '#000000',
    '#dddddd',
    '#ffe119',
    '#e6194b',
    '#3cb44b',
    '#0082c8',
    '#f58231',
    '#911eb4',
    '#46f0f0',
    '#f032e6',
    '#d2f53c',
    '#fabebe',
    '#008080',
    '#e6beff',
    '#aa6e28',
    '#808000'
]

route_names = [
    'Route 01 - Abolições/Santa Delmira/Redenção',
    'Route 02 - Abolições/Santa Delmira/Redenção',
    'Route 03 - Santo Antônio/Barrocas',
    'Route 04 - Abolição V',
    'Route 05 - Vingt Rosado/Universidades',
    'Route 06 - Universidades',
    'Route 07 - Nova Vida',
    'Route 08 - Sumaré',
    'Route 09 - Bom Jesus/Belo Horizonte',
    'Route 10 - Shopping',
    'Route 11 - Parque Universitário/UNIRB/Alto das Brisas',
    'Route 12 - Nova Mossoró/Resistência/Santo Antônio',
    'Route 14 - Aeroporto/Rodoviária',
    'Route 15 - Macarrão/Boa Vista/Centro',
    'Route 17 - Odete Rosado',
    'Route 19 - Cidade Oeste'
]

# Loop over routes and corresponding colors
for route, color, name in zip(routes, route_colors, route_names):
    latitudes = [coord[1] for coord in route]  # Extract latitude
    longitudes = [coord[0] for coord in route]  # Extract longitude

    fig.add_trace(go.Scattermapbox(
        mode="lines",
        lon=longitudes,
        lat=latitudes,
        line=dict(width=2, color=color),
        name=name
    ))

# Set the layout for the map
fig.update_layout(
    mapbox={
        'style': 'carto-positron',
        'zoom': 11.34,
        'center': {'lat': -5.181, 'lon': -37.34}
    },
    margin=dict(l=0, r=0, t=0, b=0),
    legend=dict(font=dict(family='Times New Roman', size=9))
)

# Show the plot
#fig.show()

fig.write_image('routes.pdf')
