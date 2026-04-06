---
title: ScheduleControl
description: Generate a Schedule Time Input
---

# ScheduleControl

The `ScheduleControl` component allows you to establish a date, hour, minute, and second where a specific event triggers. It wraps the HTML5 datetime-local input with proper styling.

## Basic Usage

```java
ScheduleControl schedule = new ScheduleControl("eventTime");
schedule.setValue("2026-04-10T15:30:00");
```

## Methods
- `setValue(String datetime)`: Sets the current selected datetime.
- `setMin(String min)`: Sets the minimum datetime allowed.
- `setMax(String max)`: Sets the maximum datetime allowed.
- `setUpdate(String ids)`: Binds a visual update event when the time is modified.
