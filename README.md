# Concourse SDK for Java

Java Library for programmatically generating type safe [Concourse](https://concourse-ci.org) Pipelines.

## Motivation

Concourse Pipelines are written in YAML which has no syntax highlighting or error checking. This can lead to forgetting
valid syntax or forgetting where various hooks and modifiers can be placed.

One day, I was in the office working with someone on updating a new pipeline, and we went to run Fly Set Pipeline only
to be met with a validation error. We joked that we should just make a wrapper for the Concourse pipeline syntax in
something like Python or Java, and thus an idea was born.

This package is meant to be used as a dependency in a custom pipeline project that would benefit from adding dynamically
generated Steps, Jobs, and Resources.

## Usage

All Pipelines generated by this SDK are outputted in JSON. Concourse Pipelines are generally set in YAML, however,
Pipelines can also be set in JSON.

To get started with developing type safe pipelines, add the following lines to your Maven Dependencies:

```xml
<!-- add Concourse SDK to Dependencies -->
<dependencies>
    <dependency>
        <groupId>org.concourseci</groupId>
        <artifactId>ConcourseSDK</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

Now that the SDK is added to your dependency list, you're ready to get started developing Pipelines.

### Examples

The most basic example is the Concourse Hello World Pipeline. This type of pipeline is generally good at checking that
the instance is configured correctly.

```java
public static void main(String[] args) {
    // Define Pipeline
    Pipeline pipeline = new Pipeline();

    // Define Job
    Job job = new Job("job").markPublic();

    // Define everything for the Task
    //   Create our echo of Hello world!
    Command command = Command.createCommand("echo").addArg("Hello world!");

    //   Specify an Anonymous Resource (Task Container)
    AnonymousResource busybox = new AnonymousResource(RegistryImageResourceType.getInstance(), RegistryImageConfig.create("busybox"));

    //   Put it all together in a Task Config
    TaskConfig config = TaskConfig.create(Platform.LINUX, busybox, command);

    //   Generate the Task
    Task task = new Task("simple-task", config);

    // Add the Task to the Job
    job.addStep(task);

    // Add the Job to the Pipeline
    pipeline.addJob(job);

    // Render the Pipeline
    System.out.println(pipeline.render());
}
```

Which leads to a rendered JSON of:

```json
{
  "resourceTypes": [],
  "resources": [],
  "groups": [],
  "jobs": [
    {
      "name": "job",
      "plan": [
        {
          "task": "simple-task",
          "vars": {},
          "config": {
            "platform": "linux",
            "image_resource": {
              "type": "registry-image",
              "source": {
                "repository": "busybox"
              }
            },
            "run": {
              "path": "echo",
              "args": [
                "Hello, world!"
              ]
            },
            "inputs": [],
            "outputs": []
          },
          "privileged": false
        }
      ],
      "serial": false,
      "serial_groups": [],
      "public": true
    }
  ]
}
```

## Comparison

Comparing our above Hello World Example to the example YAML exposes a pretty expected result:

```yaml
---
jobs:
  - name: job
    public: true
    plan:
      - task: simple-task
        config:
          platform: linux
          image_resource:
            type: registry-image
            source:
              reposotory: busybox
          run:
            path: echo
            args:
              - "Hello world!"
```

The Pipeline written in the SDK is fairly verbose compared to the YAML definition. However, in the above example,
we've misspelled "repository" when specifying our image which wouldn't be caught by our Concourse Instance until
trying to run the Pipeline. The SDK however, allows you to specify the actual container image without worrying about
the field names.

## Known Limitations

As with most things, there are a couple of known limitations with the SDK.

### Variable Interpolation and Identifiers

The [Pipeline Vars](https://concourse-ci.org/pipeline-vars-example.html) example on the Concourse Docs shows that
variables can be used to dynamically set the Job Names using a params file.

```yaml
---
jobs:
  - name: ((first))-job
    public: true
    plan:
      - task: simple-task
        config:
          platform: linux
          image_resource:
            type: registry-image
            source: { repository: busybox }
          run:
            path: echo
            args: [ "Hello, ((hello))!" ]
  - name: level-((number))-job
    public: true
    plan:
      - task: simple-task
        config:
          platform: linux
          image_resource:
            type: registry-image
            source: { repository: busybox }
          run:
            path: echo
            args: [ "Hello, ((hello))!" ]
```

When trying to re-create this example with the Concourse SDK however, we run into a Validation error. Certain fields
within the Concourse Schema, such as the Job name above, rely on
an [identifier schema](https://concourse-ci.org/config-basics.html#schema.identifier) that is currently treated as a
warning when using the set-pipeline commands. When using the Concourse SDK however, this turns into a full-blown error.

Working around this is trivial when creating pipelines by using string concatenation to generate the identifier instead
of relying on external vars.

## Credits

The [Concourse](https://concourse-ci.org) team for providing a predictable schema to generate this type safe SDK off of.