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

interface MapProps {
    // TODO: Define the props of this component. You will want to pass down edges
    edgeList: String[];
}

interface MapState {
}

class Map extends Component<MapProps, MapState> {
    render() {
        let mapLines = []
        for (let i = 0; i < this.props.edgeList.length; i++) {
            let coord = this.props.edgeList[i].split(" ");
            let x1 = parseInt(coord[0])
            let y1 = parseInt(coord[1])
            let x2 = parseInt(coord[2])
            let y2 = parseInt(coord[3])
            let color = coord[4]
            mapLines.push(
                  <MapLine x1={x1} y1={y1} x2={x2} y2={y2} color={color} key={i}/>
            )
        }

        // <MapLine key={key1} color="red" x1={1000} y1={1000} x2={2000} y2={2000}/>

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
