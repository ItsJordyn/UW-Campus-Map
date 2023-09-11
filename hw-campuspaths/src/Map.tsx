/*
 * Copyright (C) 2022 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import {LatLngExpression} from "leaflet";
import React, {Component} from "react";
import {MapContainer, TileLayer} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import {UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER} from "./Constants";

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

// NOTE: This component is a suggestion for you to use, if you would like to. If
// you don't want to use this component, you're free to delete it or replace it
// with your hw-lines Map

interface MapProps {
    coordinates: string[]
}

interface MapState {
}

// A visual map containing buildings with coordinates and paths between them
class Map extends Component<MapProps, MapState> {

    // renders the coordinates for each of the buildings
    render() {
        let mapLines = []
        for (let i = 0; i < this.props.coordinates.length; i++) {
            let coord = this.props.coordinates[i].split(" ");
            let color = "magenta"
            mapLines.push(
                <MapLine x1={parseFloat(coord[0])}
                         y1={parseFloat(coord[1])}
                         x2={parseFloat(coord[2])}
                         y2={parseFloat(coord[3])}
                         color={color} key={i}/>)
        }

        // returns the map and the lines of the map
        return (
            <div id="map">
                <MapContainer
                    center={position}
                    zoom={15}
                    scrollWheelZoom={false}
                >
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    {
                        mapLines
                    }
                </MapContainer>
            </div>
        );
    }
}

export default Map;
