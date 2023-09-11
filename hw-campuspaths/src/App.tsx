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

import React, {Component} from 'react';
import Dropbox from "./Dropbox";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import {stringify} from "querystring";

interface AppState {
    buildingNames: JSX.Element[]
    pathSegments: string[]
}


// Fills in the data of building names and path segments to a map of the UW campus
class App extends Component<{}, AppState> {

    constructor(props: any) {
        super(props);
        this.state = {
            buildingNames: [],
            pathSegments: [],
        };
    }

    // Loads the building data initially. Ensures the data for the buildings are loaded
    componentDidMount() {
        this.requestNames();
    }

    // Requests the names of the buildings and stores them in buildingNames
    requestNames = async () => {
        try {
            let response = await fetch("http://localhost:4567/find-buildings");
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return; // Don't keep trying to execute if the response is bad.
            }

            let namesMap = await response.json(); // this gets you the map of building names

            let namesList: JSX.Element[] = []

            for (const [short, long] of Object.entries(namesMap)) {
                let name = short + ": " + long;
                namesList.push(<option value={name}>{name}</option>)
            }

            this.setState({
                buildingNames: namesList
            });
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    // requests the path edges and stores the shortest path in pathSegments
    requestPath = async (start: string, end: string) => {
        try {
            let response = await fetch("http://localhost:4567/find-path?start=" + start
                + "&end=" + end);
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return; // Don't keep trying to execute if the response is bad.
            }

            let pathList = await response.json(); // list of paths

            let paths = []
            for (const segment of pathList["path"]) {
                let start = segment["start"];
                let end = segment["end"];
                let result = start["x"] + " " + start["y"] + " " + end["x"] + " " + end["y"]
                paths.push(result)
            }

            this.setState({
                pathSegments: paths
            });
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    // Renders/displays the HTML to the webpage
    render() {
        return (
            <div>
                <h1 id="app-title">Campus Path Mapper!</h1>
                <div>
                    <Map coordinates={this.state.pathSegments}/>
                    <Dropbox
                        buildingNames={this.state.buildingNames}
                        activateRequest={this.requestPath}
                        clear={() => this.setState({pathSegments: []})}
                    />
                </div>
            </div>
        );
    }

}

export default App;
