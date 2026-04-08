# Card Component

The `Card` component is a flexible and extensible content container. It includes options for titles, subtitles, body content, images, and child elements for actions.

## Overview

Cards are typically used to group related information and actions into a single visual unit. The `Card` component in JettraWUI provides a polished, interactive element out-of-the-box, complete with hover animations and stylized borders to fit the Cyberpunk UI theme.

### Basic Usage

Creating a simple card with a title, subtitle, and body text:

```java
import io.jettra.wui.complex.Card;

Card myCard = new Card()
    .setTitle("Card Title")
    .setSubtitle("Secondary Information")
    .setContentText("This is the main content of the card.")
    .setWidth("350px");
    
page.add(myCard);
```

### Adding Images

You can add an image to the top of the card using the `setImageUrl` method. The image will automatically span the full width of the card and maintain its aspect ratio.

```java
Card imageCard = new Card()
    .setTitle("Mountain View")
    .setContentText("Enjoy the beautiful scenery from the top of the mountains.")
    .setImageUrl("https://images.unsplash.com/photo-1549880338-65ddcdfd017b?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60")
    .setWidth("350px");

page.add(imageCard);
```

### Adding Action Buttons

The `Card` component extends `UIComponent`, allowing you to add children to it. Any children added to the card will automatically be placed at the bottom of the card in a horizontal action bar.

```java
import io.jettra.wui.components.Button;

Card actionCard = new Card()
    .setTitle("Interactive Card")
    .setContentText("This card has buttons at the bottom.")
    .setWidth("400px");

Button acceptBtn = new Button("Accept");
acceptBtn.primary();
acceptBtn.setProperty("onclick", "console.log('Accepted')");

Button dismissBtn = new Button("Dismiss");
dismissBtn.secondary();

actionCard.add(acceptBtn);
actionCard.add(dismissBtn);

page.add(actionCard);
```

## Styling and Layout

The `Card` uses Flexbox internally. By default, it sets its flex-direction to `column`, pushing the child elements (the actions) to the bottom of the card while the title and content remain at the top. This ensures all cards in a grid layout can have consistent heights with their action bars pinned to the bottom.

To change the width of the card, you can use `.setWidth("...")` which accepts standard CSS width values like `300px`, `100%`, or `20rem`.
