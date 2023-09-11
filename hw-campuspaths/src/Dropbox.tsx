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

interface DropboxProps {
    buildingNames: JSX.Element[];
    activateRequest(start: string, end: string): void
    clear(): void
}

interface DropboxState {
    start: string;
    end: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class Dropbox extends Component<DropboxProps, DropboxState> {
    constructor(props: any) {
        super(props);
        this.state = {
            start : "",
            end: "",
        };
    }

    // Clears the lines drawn on the map and the buildings selected with the dropdown boxes
    clear() {
        this.setState( {start : ""})
        this.setState( {end: ""})
        this.props.clear()
    }

    // checks that two buildings were selected with the dropdowns and assigns the start and ending
    // building to be the shortname version
    inputCheckSend() {
        if (this.state.start != "" && this.state.end != "") {
            let startName = this.state.start.split(":")[0]
            let endName = this.state.end.split(":")[0]
            this.props.activateRequest(startName, endName)
        } else {
            alert("dude you need to have both buildings selected lol")
        }
    }

    // Renders the functions of the dropdown box
    render() {
        return (
            <div>
                    <select value={this.state.start} onChange={(event) => this.setState({start: event.target.value})}>
                        <option value="">  Please choose starting building  </option>
                        {this.props.buildingNames}
                     </select>
                    <select value={this.state.end} onChange={(event) => this.setState({end: event.target.value})}>
                        <option value="">  Please choose ending building  </option>
                        {this.props.buildingNames}
                  </select>
                <button onClick={() => {this.inputCheckSend()}}>Draw</button>
                <button onClick={() => {this.clear()}}>Clear</button>
            </div>
        );
    }
}

export default Dropbox;
