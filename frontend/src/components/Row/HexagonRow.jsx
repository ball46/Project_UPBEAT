import React from "react";
import "./Hexstyle.css";
import config from "/src/config";

function HexagonRow(props) {
  const tags = [];
  const amount = config["width"];
  for (let i = 0; i < amount; i++) {
    let variant = i % 2 !== 0 ? "hexagon" : "hexagon-a";

    tags.push(
      <div class={variant}>
        <div class="content">
          {props.x},{i + 1}
        </div>
      </div>
    );
  }
  return <div class="row">{tags}</div>;
}

export default HexagonRow;
