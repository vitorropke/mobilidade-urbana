"""
This script generates population XML files for MATSim. The generated files contain person plans.

Key Features:
    - Generates person plans for different scenarios (A, B, C and D).
    - Supports customizing departure times and trip frequency.
    - Saves the generated plans in MATSim-compliant XML files.

Dependency:
    - `lxml.etree` for XML generation and manipulation.

Input File:
    - `facilities_extreme_points.xml`: A XML file containing facilities with its names and coordinates.

Output Files:
    - `population_scenario_a.xml`: Hourly schedule from 5 a.m. to 10 p.m.
    - `population_scenario_b.xml`: Hourly schedule during peak hours (6-8 a.m., 11 a.m.-1 p.m., 5-7 p.m.).
    - `population_scenario_c.xml`: Half-hourly schedule from 5 a.m. to 10:30 p.m.
    - `population_scenario_d.xml`: Minute-by-minute schedule from 5 a.m. to 10:59 p.m.
"""
from lxml import etree


def generate_person(population, origin: tuple[str, str, str], destination: tuple[str, str, str], hour: int,
                    minute: int = 0) -> None:
    """
    Adds a person and their plan to the specified population element.

    Parameters:
        population (etree.Element): The root XML element for the population.
        origin (tuple[str, str, str]): A tuple containing the ID, x-coordinate, and y-coordinate of the origin.
        destination (tuple[str, str, str]): A tuple containing the ID, x-coordinate, and y-coordinate of the
            destination.
        hour (int): The hour of the trip's departure time (0-23).
        minute (int, optional): The minute of the trip's departure time (0-59). Defaults to 0.

    Returns:
        None
    """
    person = etree.SubElement(population, 'person',
                              attrib={'id': f"{origin[0]} {destination[0]} {hour:02}:{minute:02}:00"})
    plan = etree.SubElement(person, 'plan')
    etree.SubElement(plan, 'act', attrib={'type': 'home', 'x': origin[1], 'y': origin[2],
                                          'end_time': f"{hour:02}:{minute:02}:00"})
    etree.SubElement(plan, 'leg', attrib={'mode': 'pt'})
    etree.SubElement(plan, 'act', attrib={'type': 'home', 'x': destination[1], 'y': destination[2]})


def save_file(population, file_name: str) -> None:
    """
    Saves the given population element as a MATSim-compliant XML file.

    Parameters:
        population (etree.Element): The root XML element representing the population.
        file_name (str): The name of the file to save the XML data.

    Returns:
        None
    """
    xml_declaration: str = '<?xml version="1.0" encoding="UTF-8"?>'
    doctype: str = '<!DOCTYPE plans SYSTEM "http://www.matsim.org/files/dtd/plans_v4.dtd">'
    pretty_xml: str = etree.tostring(element_or_tree=population, encoding='UTF-8', xml_declaration=False,
                                     pretty_print=True).decode('UTF-8')
    output_tree: str = f"{xml_declaration}\n{doctype}\n\n{pretty_xml}"
    with open(file=file_name, mode='w', encoding='UTF-8') as file:
        file.write(output_tree)


def main() -> None:
    """
    Orchestrates the generation of MATSim-compliant population XML files for different scenarios.

    Returns:
        None
    """
    input_tree = etree.parse('facilities_extreme_points.xml')
    input_root = input_tree.getroot()

    selected_bus_stops: list[tuple[str, str, str]] = []

    for facilities in input_root.iter('facility'):
        selected_bus_stops.append((facilities.attrib['id'], facilities.attrib['x'], facilities.attrib['y']))

    population_scenario_a = etree.Element('plans')
    population_scenario_b = etree.Element('plans')
    population_scenario_c = etree.Element('plans')
    population_scenario_d = etree.Element('plans')

    peak_hours: list[int] = [6, 7, 8, 11, 12, 13, 17, 18, 19]

    for origin in selected_bus_stops:
        for destination in selected_bus_stops:
            if origin != destination:
                for hour in range(5, 23):
                    generate_person(population=population_scenario_a, origin=origin, destination=destination, hour=hour)

                    if hour in peak_hours:
                        generate_person(population=population_scenario_b, origin=origin, destination=destination,
                                        hour=hour)

                    for minute in range(0, 60):
                        generate_person(population=population_scenario_d, origin=origin, destination=destination,
                                        hour=hour,
                                        minute=minute)

                        if (minute % 30) == 0:
                            generate_person(population=population_scenario_c, origin=origin, destination=destination,
                                            hour=hour, minute=minute)

    save_file(population=population_scenario_a, file_name='population_scenario_a.xml')
    save_file(population=population_scenario_b, file_name='population_scenario_b.xml')
    save_file(population=population_scenario_c, file_name='population_scenario_c.xml')
    save_file(population=population_scenario_d, file_name='population_scenario_d.xml')


if __name__ == '__main__':
    main()
