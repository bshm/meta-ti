SECTION = "kernel"
DESCRIPTION = "Linux kernel for TI33x EVM from PSP, based on am335x-kernel"
LICENSE = "GPLv2"
KERNEL_IMAGETYPE = "uImage"

require multi-kernel.inc

S = "${WORKDIR}/git"

MULTI_CONFIG_BASE_SUFFIX = ""

BRANCH = "v3.2-staging"
SRCREV = "09e9651bcf2ee8d86685f2a8075bc6557b1d3b91"
MACHINE_KERNEL_PR_append = "a+gitr${SRCREV}"

COMPATIBLE_MACHINE = "(ti33x)"

THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
CONFIGS_PSP = "${@base_set_filespath(["${THISDIR}/${PN}-${PV}/tipspkernel"], d)}:\
${@base_set_filespath(["${THISDIR}/${PN}/tipspkernel"], d)}:\
${@base_set_filespath(["${THISDIR}/files/tipspkernel"], d)}:"
FILESPATH =. "${@base_contains('DISTRO_FEATURES', 'tipspkernel', "${CONFIGS_PSP}", "", d)}"

SRC_URI += "git://arago-project.org/git/projects/linux-am33x.git;protocol=http;branch=${BRANCH} \
	file://defconfig"

PATCHES_OVER_PSP = " \
	file://3.2.1/0001-MAINTAINERS-stable-Update-address.patch \
	file://3.2.1/0002-Documentation-Update-stable-address.patch \
	file://3.2.1/0003-firmware-Fix-an-oops-on-reading-fw_priv-fw-in-sysfs-.patch \
	file://3.2.1/0004-rt2800usb-Move-ID-out-of-unknown.patch \
	file://3.2.1/0005-offb-Fix-setting-of-the-pseudo-palette-for-8bpp.patch \
	file://3.2.1/0006-offb-Fix-bug-in-calculating-requested-vram-size.patch \
	file://3.2.1/0007-libertas-clean-up-scan-thread-handling.patch \
	file://3.2.1/0008-bcma-support-for-suspend-and-resume.patch \
	file://3.2.1/0009-wl12xx-Validate-FEM-index-from-ini-file-and-FW.patch \
	file://3.2.1/0010-wl12xx-Check-buffer-bound-when-processing-nvs-data.patch \
	file://3.2.1/0011-wl12xx-Restore-testmode-ABI.patch \
	file://3.2.1/0012-powerpc-time-Handle-wrapping-of-decrementer.patch \
	file://3.2.1/0013-powerpc-Fix-unpaired-probe_hcall_entry-and-probe_hca.patch \
	file://3.2.1/0014-IB-qib-Fix-a-possible-data-corruption-when-receiving.patch \
	file://3.2.1/0015-IB-uverbs-Protect-QP-multicast-list.patch \
	file://3.2.1/0016-iwlagn-fix-TID-use-bug.patch \
	file://3.2.1/0017-iwlagn-fix-remove-use-of-PAGE_SIZE.patch \
	file://3.2.1/0018-perf-Fix-parsing-of-__print_flags-in-TP_printk.patch \
	file://3.2.1/0019-ore-Fix-crash-in-case-of-an-IO-error.patch \
	file://3.2.1/0020-ore-fix-BUG_ON-too-few-sgs-when-reading.patch \
	file://3.2.1/0021-ore-Must-support-none-PAGE-aligned-IO.patch \
	file://3.2.1/0022-ore-FIX-breakage-when-MISC_FILESYSTEMS-is-not-set.patch \
	file://3.2.1/0023-reiserfs-Fix-quota-mount-option-parsing.patch \
	file://3.2.1/0024-reiserfs-Force-inode-evictions-before-umount-to-avoi.patch \
	file://3.2.1/0025-ext3-Don-t-warn-from-writepage-when-readonly-inode-i.patch \
	file://3.2.1/0026-drivers-hv-Don-t-OOPS-when-you-cannot-init-vmbus.patch \
	file://3.2.1/0027-Drivers-hv-Fix-a-bug-in-vmbus_driver_unregister.patch \
	file://3.2.1/0028-USB-update-documentation-for-usbmon.patch \
	file://3.2.1/0029-usbfs-Fix-oops-related-to-user-namespace-conversion.patch \
	file://3.2.1/0030-atmel_serial-fix-spinlock-lockup-in-RS485-code.patch \
	file://3.2.1/0031-cgroup-fix-to-allow-mounting-a-hierarchy-by-name.patch \
	file://3.2.1/0032-udf-Fix-deadlock-when-converting-file-from-in-ICB-on.patch \
	file://3.2.1/0033-drivers-usb-class-cdc-acm.c-clear-dangling-pointer.patch \
	file://3.2.1/0034-USB-isight-fix-kernel-bug-when-loading-firmware.patch \
	file://3.2.1/0035-usb-usb-storage-doesn-t-support-dynamic-id-currently.patch \
	file://3.2.1/0036-USB-pxa168-Fix-compilation-error.patch \
	file://3.2.1/0037-USB-add-quirk-for-another-camera.patch \
	file://3.2.1/0038-USB-omninet-fix-write_room.patch \
	file://3.2.1/0039-usb-option-add-ZD-Incorporated-HSPA-modem.patch \
	file://3.2.1/0040-USB-Add-USB-ID-for-Multiplex-RC-serial-adapter-to-cp.patch \
	file://3.2.1/0041-usb-fix-number-of-mapped-SG-DMA-entries.patch \
	file://3.2.1/0042-xhci-Properly-handle-COMP_2ND_BW_ERR.patch \
	file://3.2.1/0043-usb-ch9-fix-up-MaxStreams-helper.patch \
	file://3.2.1/0044-igmp-Avoid-zero-delay-when-receiving-odd-mixture-of-.patch \
	file://3.2.1/0045-asix-fix-infinite-loop-in-rx_fixup.patch \
	file://3.2.1/0046-bonding-fix-error-handling-if-slave-is-busy-v2.patch \
	file://3.2.1/0047-usb-cdc-acm-Fix-acm_tty_hangup-vs.-acm_tty_close-rac.patch \
	file://3.2.1/0048-xfs-fix-acl-count-validation-in-xfs_acl_from_disk.patch \
	file://3.2.1/0049-Linux-3.2.1.patch \
	file://3.2.2/0001-mtdoops-fix-the-oops_page_used-array-size.patch \
	file://3.2.2/0002-mtd-mtdoops-skip-reading-initially-bad-blocks.patch \
	file://3.2.2/0003-mtd-mtd_blkdevs-don-t-increase-open-count-on-error-p.patch \
	file://3.2.2/0004-mtd-tests-stresstest-bail-out-if-device-has-not-enou.patch \
	file://3.2.2/0005-drivers-rtc-interface.c-fix-alarm-rollover-when-day-.patch \
	file://3.2.2/0006-ext4-add-missing-ext4_resize_end-on-error-paths.patch \
	file://3.2.2/0007-ext4-fix-undefined-behavior-in-ext4_fill_flex_info.patch \
	file://3.2.2/0008-ALSA-snd-usb-us122l-Delete-calls-to-preempt_disable.patch \
	file://3.2.2/0009-ALSA-HDA-Fix-master-control-for-Cirrus-Logic-421X.patch \
	file://3.2.2/0010-ALSA-HDA-Fix-automute-for-Cirrus-Logic-421x.patch \
	file://3.2.2/0011-ALSA-ice1724-Check-for-ac97-to-avoid-kernel-oops.patch \
	file://3.2.2/0012-ALSA-usb-audio-Avoid-flood-of-frame-active-debug-mes.patch \
	file://3.2.2/0013-ALSA-hda-Use-auto-parser-for-HP-laptops-with-cx20459.patch \
	file://3.2.2/0014-ALSA-hda-Return-the-error-from-get_wcaps_type-for-in.patch \
	file://3.2.2/0015-ALSA-hda-Fix-the-detection-of-Loopback-Mixing-contro.patch \
	file://3.2.2/0016-ALSA-hda-Fix-the-lost-power-setup-of-seconary-pins-a.patch \
	file://3.2.2/0017-drm-radeon-kms-workaround-invalid-AVI-infoframe-chec.patch \
	file://3.2.2/0018-drm-radeon-kms-disable-writeback-on-pre-R300-asics.patch \
	file://3.2.2/0019-radeon-Fix-disabling-PCI-bus-mastering-on-big-endian.patch \
	file://3.2.2/0020-pnfs-obj-pNFS-errors-are-communicated-on-iodata-pnfs.patch \
	file://3.2.2/0021-pnfs-obj-Must-return-layout-on-IO-error.patch \
	file://3.2.2/0022-NFS-Retry-mounting-NFSROOT.patch \
	file://3.2.2/0023-NFSv4.1-fix-backchannel-slotid-off-by-one-bug.patch \
	file://3.2.2/0024-NFS-fix-recent-breakage-to-NFS-error-handling.patch \
	file://3.2.2/0025-NFSv4-include-bitmap-in-nfsv4-get-acl-data.patch \
	file://3.2.2/0026-nfs-fix-regression-in-handling-of-context-option-in-.patch \
	file://3.2.2/0027-HID-bump-maximum-global-item-tag-report-size-to-96-b.patch \
	file://3.2.2/0028-HID-wiimote-Select-INPUT_FF_MEMLESS.patch \
	file://3.2.2/0029-UBI-fix-missing-scrub-when-there-is-a-bit-flip.patch \
	file://3.2.2/0030-UBI-fix-use-after-free-on-error-path.patch \
	file://3.2.2/0031-PCI-Fix-PCI_EXP_TYPE_RC_EC-value.patch \
	file://3.2.2/0032-PCI-msi-Disable-msi-interrupts-when-we-initialize-a-.patch \
	file://3.2.2/0033-x86-PCI-Ignore-CPU-non-addressable-_CRS-reserved-mem.patch \
	file://3.2.2/0034-x86-PCI-amd-factor-out-MMCONFIG-discovery.patch \
	file://3.2.2/0035-x86-PCI-build-amd_bus.o-only-when-CONFIG_AMD_NB-y.patch \
	file://3.2.2/0036-SCSI-mpt2sas-Release-spinlock-for-the-raid-device-li.patch \
	file://3.2.2/0037-SCSI-mpt2sas-Fix-for-memory-allocation-error-for-lar.patch \
	file://3.2.2/0038-xen-xenbus-Reject-replies-with-payload-XENSTORE_PAYL.patch \
	file://3.2.2/0039-md-raid1-perform-bad-block-tests-for-WriteMostly-dev.patch \
	file://3.2.2/0040-ima-free-duplicate-measurement-memory.patch \
	file://3.2.2/0041-ima-fix-invalid-memory-reference.patch \
	file://3.2.2/0042-slub-fix-a-possible-memleak-in-__slab_alloc.patch \
	file://3.2.2/0043-PNP-work-around-Dell-1536-1546-BIOS-MMCONFIG-bug-tha.patch \
	file://3.2.2/0044-asix-fix-setting-custom-MAC-address-on-Asix-88178-de.patch \
	file://3.2.2/0045-asix-fix-setting-custom-MAC-address-on-Asix-88772-de.patch \
	file://3.2.2/0046-include-linux-crash_dump.h-needs-elf.h.patch \
	file://3.2.2/0047-rtl8192se-Fix-BUG-caused-by-failure-to-check-skb-all.patch \
	file://3.2.2/0048-mac80211-fix-rx-key-NULL-pointer-dereference-in-prom.patch \
	file://3.2.2/0049-ath9k-Fix-regression-in-channelwidth-switch-at-the-s.patch \
	file://3.2.2/0050-memcg-add-mem_cgroup_replace_page_cache-to-fix-LRU-i.patch \
	file://3.2.2/0051-x86-Fix-mmap-random-address-range.patch \
	file://3.2.2/0052-UBI-fix-nameless-volumes-handling.patch \
	file://3.2.2/0053-UBI-fix-debugging-messages.patch \
	file://3.2.2/0054-UBI-make-vid_hdr-non-static.patch \
	file://3.2.2/0055-UBIFS-fix-debugging-messages.patch \
	file://3.2.2/0056-UBIFS-make-debugging-messages-light-again.patch \
	file://3.2.2/0057-i2c-Fix-error-value-returned-by-several-bus-drivers.patch \
	file://3.2.2/0058-mmc-core-Fix-voltage-select-in-DDR-mode.patch \
	file://3.2.2/0059-mmc-sdhci-Fix-tuning-timer-incorrect-setting-when-su.patch \
	file://3.2.2/0060-mmc-sd-Fix-SDR12-timing-regression.patch \
	file://3.2.2/0061-V4L-DVB-v4l2-ioctl-integer-overflow-in-video_usercop.patch \
	file://3.2.2/0062-Unused-iocbs-in-a-batch-should-not-be-accounted-as-a.patch \
	file://3.2.2/0063-ftrace-Fix-unregister-ftrace_ops-accounting.patch \
	file://3.2.2/0064-kconfig-streamline-config.pl-Simplify-backslash-line.patch \
	file://3.2.2/0065-kconfig-streamline-config.pl-Fix-parsing-Makefile-wi.patch \
	file://3.2.2/0066-svcrpc-fix-double-free-on-shutdown-of-nfsd-after-cha.patch \
	file://3.2.2/0067-svcrpc-destroy-server-sockets-all-at-once.patch \
	file://3.2.2/0068-svcrpc-avoid-memory-corruption-on-pool-shutdown.patch \
	file://3.2.2/0069-nfsd4-fix-lockowner-matching.patch \
	file://3.2.2/0070-nfsd-Fix-oops-when-parsing-a-0-length-export.patch \
	file://3.2.2/0071-fsnotify-don-t-BUG-in-fsnotify_destroy_mark.patch \
	file://3.2.2/0072-x86-UV-Update-Boot-messages-for-SGI-UV2-platform.patch \
	file://3.2.2/0073-recordmcount-Fix-handling-of-elf64-big-endian-object.patch \
	file://3.2.2/0074-uvcvideo-Fix-integer-overflow-in-uvc_ioctl_ctrl_map.patch \
	file://3.2.2/0075-dcache-use-a-dispose-list-in-select_parent.patch \
	file://3.2.2/0076-fix-shrink_dcache_parent-livelock.patch \
	file://3.2.2/0077-pnfsblock-acquire-im_lock-in-_preload_range.patch \
	file://3.2.2/0078-pnfsblock-don-t-spinlock-when-freeing-block_dev.patch \
	file://3.2.2/0079-pnfsblock-limit-bio-page-count.patch \
	file://3.2.2/0080-mac80211-revert-on-channel-work-optimisations.patch \
	file://3.2.2/0081-HID-hid-multitouch-add-another-eGalax-id.patch \
	file://3.2.2/0082-HID-multitouch-cleanup-with-eGalax-PID-definitions.patch \
	file://3.2.2/0083-HID-multitouch-Add-egalax-ID-for-Acer-Iconia-W500.patch \
	file://3.2.2/0084-HID-multitouch-add-support-for-the-MSI-Windpad-110W.patch \
	file://3.2.2/0085-HID-hid-multitouch-add-support-for-new-Hanvon-panels.patch \
	file://3.2.2/0086-HID-multitouch-add-support-of-Atmel-multitouch-panel.patch \
	file://3.2.2/0087-HID-multitouch-add-support-for-3M-32.patch \
	file://3.2.2/0088-HID-hid-multitouch-add-support-9-new-Xiroku-devices.patch \
	file://3.2.2/0089-fix-cputime-overflow-in-uptime_proc_show.patch \
	file://3.2.2/0090-block-add-and-use-scsi_blk_cmd_ioctl.patch \
	file://3.2.2/0091-block-fail-SCSI-passthrough-ioctls-on-partition-devi.patch \
	file://3.2.2/0092-dm-do-not-forward-ioctls-from-logical-volumes-to-the.patch \
	file://3.2.2/0093-proc-clean-up-and-fix-proc-pid-mem-handling.patch \
	file://3.2.2/0094-ALSA-HDA-Use-LPIB-position-fix-for-Macbook-Pro-7-1.patch \
	file://3.2.2/0095-ALSA-virtuoso-Xonar-DS-fix-polarity-of-front-output.patch \
	file://3.2.2/0096-ALSA-HDA-Fix-internal-microphone-on-Dell-Studio-16-X.patch \
	file://3.2.2/0097-TOMOYO-Accept-000-as-a-valid-character.patch \
	file://3.2.2/0098-intel-idle-Make-idle-driver-more-robust.patch \
	file://3.2.2/0099-intel_idle-fix-API-misuse.patch \
	file://3.2.2/0100-ACPI-Store-SRAT-table-revision.patch \
	file://3.2.2/0101-ACPI-x86-Use-SRAT-table-rev-to-use-8bit-or-32bit-PXM.patch \
	file://3.2.2/0102-ACPI-ia64-Use-SRAT-table-rev-to-use-8bit-or-16-32bit.patch \
	file://3.2.2/0103-ACPICA-Put-back-the-call-to-acpi_os_validate_address.patch \
	file://3.2.2/0104-ACPI-processor-fix-acpi_get_cpuid-for-UP-processor.patch \
	file://3.2.2/0105-sym53c8xx-Fix-NULL-pointer-dereference-in-slave_dest.patch \
	file://3.2.2/0106-target-Set-response-format-in-INQUIRY-response.patch \
	file://3.2.2/0107-target-Set-additional-sense-length-field-in-sense-da.patch \
	file://3.2.2/0108-bcma-invalidate-the-mapped-core-over-suspend-resume.patch \
	file://3.2.2/0109-cx23885-dvb-check-if-dvb_attach-succeded.patch \
	file://3.2.2/0110-cx88-fix-don-t-duplicate-xc4000-entry-for-radio.patch \
	file://3.2.2/0111-tuner-Fix-numberspace-conflict-between-xc4000-and-pt.patch \
	file://3.2.2/0112-tracepoints-module-Fix-disabling-tracepoints-with-ta.patch \
	file://3.2.2/0113-I2C-OMAP-correct-SYSC-register-offset-for-OMAP4.patch \
	file://3.2.2/0114-x86-UV2-Fix-new-UV2-hardware-by-using-native-UV2-bro.patch \
	file://3.2.2/0115-x86-UV2-Fix-BAU-destination-timeout-initialization.patch \
	file://3.2.2/0116-x86-UV2-Work-around-BAU-bug.patch \
	file://3.2.2/0117-ath9k_hw-fix-interpretation-of-the-rx-KeyMiss-flag.patch \
	file://3.2.2/0118-rt2800pci-fix-spurious-interrupts-generation.patch \
	file://3.2.2/0119-xfs-fix-endian-conversion-issue-in-discard-code.patch \
	file://3.2.2/0120-i2c-eg20t-modified-the-setting-of-transfer-rate.patch \
	file://3.2.2/0121-score-fix-off-by-one-index-into-syscall-table.patch \
	file://3.2.2/0122-cifs-lower-default-wsize-when-unix-extensions-are-no.patch \
	file://3.2.2/0123-kprobes-initialize-before-using-a-hlist.patch \
	file://3.2.2/0124-proc-clear_refs-do-not-clear-reserved-pages.patch \
	file://3.2.2/0125-mm-fix-NULL-ptr-dereference-in-__count_immobile_page.patch \
	file://3.2.2/0126-iwlagn-check-for-SMPS-mode.patch \
	file://3.2.2/0127-iwlegacy-3945-fix-hw-passive-scan-on-radar-channels.patch \
	file://3.2.2/0128-SHM_UNLOCK-fix-long-unpreemptible-section.patch \
	file://3.2.2/0129-SHM_UNLOCK-fix-Unevictable-pages-stranded-after-swap.patch \
	file://3.2.2/0130-Linux-3.2.2.patch \
	file://3.2.3/0001-ALSA-hda-Fix-buffer-alignment-regression-with-Nvidia.patch \
	file://3.2.3/0002-ALSA-hda-Fix-silent-outputs-from-docking-station-jac.patch \
	file://3.2.3/0003-eCryptfs-Sanitize-write-counts-of-dev-ecryptfs.patch \
	file://3.2.3/0004-ecryptfs-Improve-metadata-read-failure-logging.patch \
	file://3.2.3/0005-eCryptfs-Make-truncate-path-killable.patch \
	file://3.2.3/0006-eCryptfs-Check-inode-changes-in-setattr.patch \
	file://3.2.3/0007-eCryptfs-Fix-oops-when-printing-debug-info-in-extent.patch \
	file://3.2.3/0008-drm-radeon-kms-Add-an-MSI-quirk-for-Dell-RS690.patch \
	file://3.2.3/0009-drm-radeon-kms-move-panel-mode-setup-into-encoder-mo.patch \
	file://3.2.3/0010-drm-radeon-kms-rework-modeset-sequence-for-DCE41-and.patch \
	file://3.2.3/0011-drm-Fix-authentication-kernel-crash.patch \
	file://3.2.3/0012-xfs-Fix-missing-xfs_iunlock-on-error-recovery-path-i.patch \
	file://3.2.3/0013-ASoC-Mark-WM5100-register-map-cache-only-when-going-.patch \
	file://3.2.3/0014-ASoC-Disable-register-synchronisation-for-low-freque.patch \
	file://3.2.3/0015-ASoC-Don-t-go-through-cache-when-applying-WM5100-rev.patch \
	file://3.2.3/0016-ASoC-wm8996-Call-_POST_PMU-callback-for-CPVDD.patch \
	file://3.2.3/0017-brcmsmac-fix-tx-queue-flush-infinite-loop.patch \
	file://3.2.3/0018-mac80211-fix-work-removal-on-deauth-request.patch \
	file://3.2.3/0019-jbd-Issue-cache-flush-after-checkpointing.patch \
	file://3.2.3/0020-crypto-sha512-make-it-work-undo-percpu-message-sched.patch \
	file://3.2.3/0021-crypto-sha512-reduce-stack-usage-to-safe-number.patch \
	file://3.2.3/0022-tpm_tis-add-delay-after-aborting-command.patch \
	file://3.2.3/0023-x86-uv-Fix-uninitialized-spinlocks.patch \
	file://3.2.3/0024-x86-uv-Fix-uv_gpa_to_soc_phys_ram-shift.patch \
	file://3.2.3/0025-x86-microcode_amd-Add-support-for-CPU-family-specifi.patch \
	file://3.2.3/0026-m68k-Fix-assembler-constraint-to-prevent-overeager-g.patch \
	file://3.2.3/0027-ALSA-hda-set-mute-led-polarity-for-laptops-with-bugg.patch \
	file://3.2.3/0028-ALSA-hda-Fix-silent-output-on-ASUS-A6Rp.patch \
	file://3.2.3/0029-ALSA-hda-Fix-silent-output-on-Haier-W18-laptop.patch \
	file://3.2.3/0030-drm-i915-paper-over-missed-irq-issues-with-force-wak.patch \
	file://3.2.3/0031-drm-i915-sdvo-always-set-positive-sync-polarity.patch \
	file://3.2.3/0032-drm-i915-Re-enable-gen7-RC6-and-GPU-turbo-after-resu.patch \
	file://3.2.3/0033-ARM-at91-fix-at91rm9200-soc-subtype-handling.patch \
	file://3.2.3/0034-mach-ux500-enable-ARM-errata-764369.patch \
	file://3.2.3/0035-ARM-7296-1-proc-v7.S-remove-HARVARD_CACHE-preprocess.patch \
	file://3.2.3/0036-sysfs-Complain-bitterly-about-attempts-to-remove-fil.patch \
	file://3.2.3/0037-x86-xen-size-struct-xen_spinlock-to-always-fit-in-ar.patch \
	file://3.2.3/0038-mpt2sas-Removed-redundant-calling-of-_scsih_probe_de.patch \
	file://3.2.3/0039-USB-option-Add-LG-docomo-L-02C.patch \
	file://3.2.3/0040-USB-ftdi_sio-fix-TIOCSSERIAL-baud_base-handling.patch \
	file://3.2.3/0041-USB-ftdi_sio-fix-initial-baud-rate.patch \
	file://3.2.3/0042-USB-ftdi_sio-add-PID-for-TI-XDS100v2-BeagleBone-A3.patch \
	file://3.2.3/0043-USB-serial-ftdi-additional-IDs.patch \
	file://3.2.3/0044-USB-ftdi_sio-Add-more-identifiers.patch \
	file://3.2.3/0045-USB-cdc-wdm-updating-desc-length-must-be-protected-b.patch \
	file://3.2.3/0046-USB-cdc-wdm-use-two-mutexes-to-allow-simultaneous-re.patch \
	file://3.2.3/0047-qcaux-add-more-Pantech-UML190-and-UML290-ports.patch \
	file://3.2.3/0048-usb-dwc3-ep0-tidy-up-Pending-Request-handling.patch \
	file://3.2.3/0049-usb-io_ti-Make-edge_remove_sysfs_attrs-the-port_remo.patch \
	file://3.2.3/0050-TTY-fix-UV-serial-console-regression.patch \
	file://3.2.3/0051-serial-amba-pl011-lock-console-writes-against-interr.patch \
	file://3.2.3/0052-jsm-Fixed-EEH-recovery-error.patch \
	file://3.2.3/0053-iwlwifi-fix-PCI-E-transport-inta-race.patch \
	file://3.2.3/0054-vmwgfx-Fix-assignment-in-vmw_framebuffer_create_hand.patch \
	file://3.2.3/0055-USB-Realtek-cr-fix-autopm-scheduling-while-atomic.patch \
	file://3.2.3/0056-USB-usbsevseg-fix-max-length.patch \
	file://3.2.3/0057-usb-gadget-langwell-don-t-call-gadget-s-disconnect.patch \
	file://3.2.3/0058-usb-gadget-storage-endian-fix.patch \
	file://3.2.3/0059-drivers-usb-host-ehci-fsl.c-add-missing-iounmap.patch \
	file://3.2.3/0060-xhci-Fix-USB-3.0-device-restart-on-resume.patch \
	file://3.2.3/0061-xHCI-Cleanup-isoc-transfer-ring-when-TD-length-misma.patch \
	file://3.2.3/0062-usb-musb-davinci-fix-build-breakage.patch \
	file://3.2.3/0063-hwmon-f71805f-Fix-clamping-of-temperature-limits.patch \
	file://3.2.3/0064-hwmon-w83627ehf-Disable-setting-DC-mode-for-pwm2-pwm.patch \
	file://3.2.3/0065-hwmon-sht15-fix-bad-error-code.patch \
	file://3.2.3/0066-USB-cdc-wdm-call-wake_up_all-to-allow-driver-to-shut.patch \
	file://3.2.3/0067-USB-cdc-wdm-better-allocate-a-buffer-that-is-at-leas.patch \
	file://3.2.3/0068-USB-cdc-wdm-Avoid-hanging-on-interface-with-no-USB_C.patch \
	file://3.2.3/0069-netns-fix-net_alloc_generic.patch \
	file://3.2.3/0070-netns-Fail-conspicously-if-someone-uses-net_generic-.patch \
	file://3.2.3/0071-net-caif-Register-properly-as-a-pernet-subsystem.patch \
	file://3.2.3/0072-af_unix-fix-EPOLLET-regression-for-stream-sockets.patch \
	file://3.2.3/0073-bonding-fix-enslaving-in-alb-mode-when-link-down.patch \
	file://3.2.3/0074-l2tp-l2tp_ip-fix-possible-oops-on-packet-receive.patch \
	file://3.2.3/0075-macvlan-fix-a-possible-use-after-free.patch \
	file://3.2.3/0076-net-bpf_jit-fix-divide-by-0-generation.patch \
	file://3.2.3/0077-net-reintroduce-missing-rcu_assign_pointer-calls.patch \
	file://3.2.3/0078-rds-Make-rds_sock_lock-BH-rather-than-IRQ-safe.patch \
	file://3.2.3/0079-tcp-fix-tcp_trim_head-to-adjust-segment-count-with-s.patch \
	file://3.2.3/0080-tcp-md5-using-remote-adress-for-md5-lookup-in-rst-pa.patch \
	file://3.2.3/0081-USB-serial-CP210x-Added-USB-ID-for-the-Link-Instrume.patch \
	file://3.2.3/0082-USB-cp210x-call-generic-open-last-in-open.patch \
	file://3.2.3/0083-USB-cp210x-fix-CP2104-baudrate-usage.patch \
	file://3.2.3/0084-USB-cp210x-do-not-map-baud-rates-to-B0.patch \
	file://3.2.3/0085-USB-cp210x-fix-up-set_termios-variables.patch \
	file://3.2.3/0086-USB-cp210x-clean-up-refactor-and-document-speed-hand.patch \
	file://3.2.3/0087-USB-cp210x-initialise-baud-rate-at-open.patch \
	file://3.2.3/0088-USB-cp210x-allow-more-baud-rates-above-1Mbaud.patch \
	file://3.2.3/0089-mach-ux500-no-MMC_CAP_SD_HIGHSPEED-on-Snowball.patch \
	file://3.2.3/0090-Linux-3.2.3.patch \
	file://3.2.4/0001-Revert-ASoC-Mark-WM5100-register-map-cache-only-when.patch \
	file://3.2.4/0002-Revert-ASoC-Don-t-go-through-cache-when-applying-WM5.patch \
	file://3.2.4/0003-Linux-3.2.4.patch \
	file://3.2.5/0001-PCI-Rework-ASPM-disable-code.patch \
	file://3.2.5/0002-Linux-3.2.5.patch \
	file://3.2.6/0001-readahead-fix-pipeline-break-caused-by-block-plug.patch \
	file://3.2.6/0002-ALSA-hda-Fix-the-logic-to-detect-VIA-analog-low-curr.patch \
	file://3.2.6/0003-ALSA-HDA-Remove-quirk-for-Asus-N53Jq.patch \
	file://3.2.6/0004-ALSA-hda-Apply-0x0f-VREF-fix-to-all-ASUS-laptops-wit.patch \
	file://3.2.6/0005-ALSA-hda-Fix-calling-cs_automic-twice-for-Cirrus-cod.patch \
	file://3.2.6/0006-ALSA-hda-Allow-analog-low-current-mode-when-dynamic-.patch \
	file://3.2.6/0007-ALSA-HDA-Fix-duplicated-output-to-more-than-one-code.patch \
	file://3.2.6/0008-ALSA-hda-Disable-dynamic-power-control-for-VIA-as-de.patch \
	file://3.2.6/0009-ASoC-wm_hubs-Enable-line-out-VMID-buffer-for-single-.patch \
	file://3.2.6/0010-ASoC-wm_hubs-fix-wrong-bits-for-LINEOUT2-N-P-mixer.patch \
	file://3.2.6/0011-ARM-7306-1-vfp-flush-thread-hwstate-before-restoring.patch \
	file://3.2.6/0012-ARM-7307-1-vfp-fix-ptrace-regset-modification-race.patch \
	file://3.2.6/0013-ARM-7308-1-vfp-flush-thread-hwstate-before-copying-p.patch \
	file://3.2.6/0014-ARM-OMAP2-GPMC-fix-device-size-setup.patch \
	file://3.2.6/0015-drivers-tty-vt-vt_ioctl.c-fix-KDFONTOP-32bit-compati.patch \
	file://3.2.6/0016-proc-mem_release-should-check-mm-NULL.patch \
	file://3.2.6/0017-proc-unify-mem_read-and-mem_write.patch \
	file://3.2.6/0018-proc-make-sure-mem_open-doesn-t-pin-the-target-s-mem.patch \
	file://3.2.6/0019-firewire-ohci-add-reset-packet-quirk-for-SB-Audigy.patch \
	file://3.2.6/0020-firewire-ohci-disable-MSI-on-Ricoh-controllers.patch \
	file://3.2.6/0021-IB-mlx4-pass-SMP-vendor-specific-attribute-MADs-to-f.patch \
	file://3.2.6/0022-RDMA-core-Fix-kernel-panic-by-always-initializing-qp.patch \
	file://3.2.6/0023-kprobes-fix-a-memory-leak-in-function-pre_handler_kr.patch \
	file://3.2.6/0024-mtd-gpmi-nand-bugfix-reset-the-BCH-module-when-it-is.patch \
	file://3.2.6/0025-Revert-mtd-atmel_nand-optimize-read-write-buffer-fun.patch \
	file://3.2.6/0026-at_hdmac-bugfix-for-enabling-channel-irq.patch \
	file://3.2.6/0027-mm-filemap_xip.c-fix-race-condition-in-xip_file_faul.patch \
	file://3.2.6/0028-mm-compaction-check-pfn_valid-when-entering-a-new-MA.patch \
	file://3.2.6/0029-PM-Hibernate-Fix-s2disk-regression-related-to-freezi.patch \
	file://3.2.6/0030-PM-QoS-CPU-C-state-breakage-with-PM-Qos-change.patch \
	file://3.2.6/0031-drm-radeon-Set-DESKTOP_HEIGHT-register-to-the-frameb.patch \
	file://3.2.6/0032-drm-nouveau-gem-fix-fence_sync-race-oops.patch \
	file://3.2.6/0033-drm-radeon-kms-disable-output-polling-when-suspended.patch \
	file://3.2.6/0034-drm-radeon-kms-fix-TRAVIS-panel-setup.patch \
	file://3.2.6/0035-sched-rt-Fix-task-stack-corruption-under-__ARCH_WANT.patch \
	file://3.2.6/0036-PM-Hibernate-Thaw-processes-in-SNAPSHOT_CREATE_IMAGE.patch \
	file://3.2.6/0037-PM-Hibernate-Thaw-kernel-threads-in-SNAPSHOT_CREATE_.patch \
	file://3.2.6/0038-8139cp-fix-missing-napi_gro_flush.patch \
	file://3.2.6/0039-udf-Mark-LVID-buffer-as-uptodate-before-marking-it-d.patch \
	file://3.2.6/0040-drm-i915-HDMI-hot-remove-notification-to-audio-drive.patch \
	file://3.2.6/0041-drm-i915-DisplayPort-hot-remove-notification-to-audi.patch \
	file://3.2.6/0042-drm-i915-check-ACTHD-of-all-rings.patch \
	file://3.2.6/0043-drm-i915-Fix-TV-Out-refresh-rate.patch \
	file://3.2.6/0044-drm-i915-handle-3rd-pipe.patch \
	file://3.2.6/0045-drm-i915-convert-force_wake_get-to-func-pointer-in-t.patch \
	file://3.2.6/0046-drm-i915-protect-force_wake_-get-put-with-the-gt_loc.patch \
	file://3.2.6/0047-eCryptfs-Infinite-loop-due-to-overflow-in-ecryptfs_w.patch \
	file://3.2.6/0048-hwmon-w83627ehf-Fix-number-of-fans-for-NCT6776F.patch \
	file://3.2.6/0049-cifs-Fix-oops-in-session-setup-code-for-null-user-mo.patch \
	file://3.2.6/0050-atmel_lcdfb-fix-usage-of-CONTRAST_CTR-in-suspend-res.patch \
	file://3.2.6/0051-lockdep-bug-Exclude-TAINT_FIRMWARE_WORKAROUND-from-d.patch \
	file://3.2.6/0052-lockdep-bug-Exclude-TAINT_OOT_MODULE-from-disabling-.patch \
	file://3.2.6/0053-iscsi-target-Fix-reject-release-handling-in-iscsit_f.patch \
	file://3.2.6/0054-iscsi-target-Fix-double-list_add-with-iscsit_alloc_b.patch \
	file://3.2.6/0055-iscsi-target-Fix-discovery-with-INADDR_ANY-and-IN6AD.patch \
	file://3.2.6/0056-ASoC-wm_hubs-Fix-routing-of-input-PGAs-to-line-outpu.patch \
	file://3.2.6/0057-ASoC-wm_hubs-Correct-line-input-to-line-output-2-pat.patch \
	file://3.2.6/0058-ASoC-wm8962-Fix-word-length-configuration.patch \
	file://3.2.6/0059-ASoC-wm8994-Enabling-VMID-should-take-a-runtime-PM-r.patch \
	file://3.2.6/0060-ASoC-wm8994-Fix-typo-in-VMID-ramp-setting.patch \
	file://3.2.6/0061-pcmcia-fix-socket-refcount-decrementing-on-each-resu.patch \
	file://3.2.6/0062-ALSA-oxygen-virtuoso-fix-exchanged-L-R-volumes-of-au.patch \
	file://3.2.6/0063-iommu-amd-Work-around-broken-IVRS-tables.patch \
	file://3.2.6/0064-iommu-msm-Fix-error-handling-in-msm_iommu_unmap.patch \
	file://3.2.6/0065-mm-compaction-check-for-overlapping-nodes-during-iso.patch \
	file://3.2.6/0066-mm-fix-UP-THP-spin_is_locked-BUGs.patch \
	file://3.2.6/0067-target-Use-correct-preempted-registration-sense-code.patch \
	file://3.2.6/0068-target-Allow-PERSISTENT-RESERVE-IN-for-non-reservati.patch \
	file://3.2.6/0069-target-Correct-sense-key-for-INVALID-FIELD-IN-PARAME.patch \
	file://3.2.6/0070-target-Add-workaround-for-zero-length-control-CDB-ha.patch \
	file://3.2.6/0071-target-Return-correct-ASC-for-unimplemented-VPD-page.patch \
	file://3.2.6/0072-target-Fail-INQUIRY-commands-with-EVPD-0-but-PAGE-CO.patch \
	file://3.2.6/0073-Staging-asus_oled-fix-image-processing.patch \
	file://3.2.6/0074-Staging-asus_oled-fix-NULL-ptr-crash-on-unloading.patch \
	file://3.2.6/0075-staging-r8712u-Add-new-Sitecom-UsB-ID.patch \
	file://3.2.6/0076-staging-r8712u-Use-asynchronous-firmware-loading.patch \
	file://3.2.6/0077-usb-ch9.h-usb_endpoint_maxp-uses-__le16_to_cpu.patch \
	file://3.2.6/0078-usb-gadget-zero-fix-bug-in-loopback-autoresume-handl.patch \
	file://3.2.6/0079-usb-Skip-PCI-USB-quirk-handling-for-Netlogic-XLP.patch \
	file://3.2.6/0080-USB-usbserial-add-new-PID-number-0xa951-to-the-ftdi-.patch \
	file://3.2.6/0081-USB-add-new-zte-3g-dongle-s-pid-to-option.c.patch \
	file://3.2.6/0082-zcache-Set-SWIZ_BITS-to-8-to-reduce-tmem-bucket-lock.patch \
	file://3.2.6/0083-zcache-fix-deadlock-condition.patch \
	file://3.2.6/0084-mmc-cb710-core-Add-missing-spin_lock_init-for-irq_lo.patch \
	file://3.2.6/0085-powernow-k8-Avoid-Pstate-MSR-accesses-on-systems-sup.patch \
	file://3.2.6/0086-powernow-k8-Fix-indexing-issue.patch \
	file://3.2.6/0087-Linux-3.2.6.patch \
	file://0002-f_rndis-HACK-around-undefined-variables.patch \
	file://0003-da8xx-fb-add-DVI-support-for-beaglebone.patch \
	file://0004-beaglebone-rebase-everything-onto-3.2-WARNING-MEGAPA.patch \
	file://0005-more-beaglebone-merges.patch \
	file://0006-beaglebone-disable-tsadc.patch \
	file://0007-tscadc-Add-general-purpose-mode-untested-with-touchs.patch \
	file://0008-tscadc-Add-board-file-mfd-support-fix-warning.patch \
	file://0009-AM335X-init-tsc-bone-style-for-new-boards.patch \
	file://0010-tscadc-make-stepconfig-channel-configurable.patch \
	file://0011-tscadc-Trigger-through-sysfs.patch \
	file://0012-meta-ti-Remove-debug-messages-for-meta-ti.patch \
	file://0013-tscadc-switch-to-polling-instead-of-interrupts.patch \
	file://0014-beaglebone-fix-ADC-init.patch \
	file://0015-AM335x-MUX-add-ehrpwm1A.patch \
	file://0016-beaglebone-enable-PWM-for-lcd-backlight-backlight-is.patch \
	file://0017-omap_hsmmc-Set-dto-to-max-value-of-14-to-avoid-SD-Ca.patch \
	file://0018-beaglebone-set-default-brightness-to-50-for-pwm-back.patch \
	file://0019-st7735fb-WIP-framebuffer-driver-supporting-Adafruit-.patch \
	file://0020-beaglebone-use-P8_6-gpio1_3-as-w1-bus.patch \
	file://0021-beaglebone-add-support-for-Towertech-TT3201-CAN-cape.patch \
"

SRC_URI += "${@base_contains('DISTRO_FEATURES', 'tipspkernel', "", "${PATCHES_OVER_PSP}", d)}"
SRC_URI_append_beaglebone = " file://logo_linux_clut224.ppm"

