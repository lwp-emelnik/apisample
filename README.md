# MusicIntervals2Anki
MusicIntervals2Anki is an Android app used to create and consolidate flashcards for ear training. It is based on two types of exercize: interval identification and interval comparison.

## Installation

Users can install the app by getting an APK from [the release section](https://github.com/lwp-emelnik/musicintervals2anki/releases).

Development environment setup see [contributing](#contributing).

## Features

### Adding notes

The app allows to add notes to AnkiDroid provided the following attributes:
- sound file
- start note and octave
- direction (ascending/descending)
- timing (melodic/harmonic)
- interval
- tempo (beats per minute)
- instrument
- first note duration (optional)

Under the hood, the app also manages two additional fields: smaller and larger sound files. These fields serve as links to related notes - ones with the same parameters but having respectively smaller and larger intervals by one semitone. These fields are filled automatically and are used in interval comparison cards.

### Batch adding

One of the most useful features of the app is the ability to add notes in bulk. Users are able to specify a set of intervals, select multiple sound files, and add the whole set of intervals to Anki in one go. 

In order to specify the set of intervals being added, users can select any combination of notes, octaves, and intervals. The number of sould files must be equal to the number of intervals in the specified set. For example, the user can select 2 notes: D and F#; then select 3 octaves: 1, 2 and 6; and select 1 interval: m3. The total number of intervals in this set is 2x3x1 = 6. 

The specified sound files, when sorted alphabetically, should follow a specific order. They should be sorted first by octave, then by note, then by interval. In this way the program will know which sould file corresponds to which interval.

*add filename dialog screenshot here*

### Searching

On top of being used for setting the attrubutes of added notes, all of the inputs are also used as filters when searching for existing notes. The search count is displayed in real-time.

### Integrity check

relations, duplicates, validation

"Mark" and "Check integrity" operations are executed on the result set of this search.


### Audio capturing & extraction

Alongside the option of playing back the selected sound files, the app provdes built-in tools to capture device audio in case you want to use other apps to generate sounds.

The app also allows using video files as an audio source for notes that are being added. In such a case, the audio will be extracted before adding it to the Anki collection.

### Configuration

The app is able to create the default "music interval" note type and keep it up to date with the provided functionality. While this will be enough for most of the users, the app also allows to opt out of using the default note type, and provides the ability to switch used Anki note type and configure used fields mapping.

Besides this, the users are given the following settings:
- select the deck for adding notes to
- select whether or not audio/video files should be deleted upon insertion to Anki
- enable/disable app version log to added notes in a separate field

## Contributing

file an issue

installation

TDD
