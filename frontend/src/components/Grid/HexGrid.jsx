import React from "react";
import HexagonRow from "../Row/HexagonRow";
import "./Gridstyles.css";
import config from "/src/config";

export default function HexGrid() {
  const tags = [];
  const amount = config["height"];
  for (let i = 0; i < amount; i++) {
    tags.push(<HexagonRow x={i + 1} />);
  }
  return (
    <div class="container">
      <div>{tags}</div>
    </div>
  );
}
