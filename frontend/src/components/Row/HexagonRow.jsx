import React from "react";
import "./Hexstyle.css";

function HexagonRow(props) {
  const tags = [];
  let amount = 25;
  for (let i = 0; i < amount; i++) {
    tags.push(
      <div class="hexagon">
        <div class="content">{props.x},{i+1}</div>
      </div>
    );
  }
  return <div class="row">{tags}</div>;
}

export default HexagonRow;
