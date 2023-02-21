import React from "react";
import "./Hexstyle.css";
import config from "/src/config";

function HexagonRow(props) {
  const tags = [];
  const amount = config["width"];
  for (let i = 0; i < amount; i++) {
    tags.push(
      <div class="hexagon">
        <div class="content">
          {props.x},{i + 1}
        </div>
      </div>
    );
  }
  return <div class="row">{tags}</div>;
}

export default HexagonRow;
